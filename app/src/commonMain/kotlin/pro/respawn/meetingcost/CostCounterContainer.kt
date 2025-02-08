@file:UseSerializers(InputSerializer::class)

package pro.respawn.meetingcost

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.StateStrategy.Atomic
import pro.respawn.flowmvi.dsl.store
import pro.respawn.flowmvi.dsl.updateState
import pro.respawn.flowmvi.dsl.updateStateImmediate
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce
import pro.respawn.flowmvi.plugins.whileSubscribed
import pro.respawn.flowmvi.savedstate.api.NullRecover
import pro.respawn.flowmvi.savedstate.api.SaveBehavior
import pro.respawn.flowmvi.savedstate.plugins.serializeState
import pro.respawn.kmmutils.inputforms.Input
import pro.respawn.kmmutils.inputforms.dsl.input
import pro.respawn.kmmutils.inputforms.dsl.isValid
import pro.respawn.kmmutils.inputforms.dsl.isValidOrEmpty
import pro.respawn.meetingcost.CostCounterState.DisplayingResult
import pro.respawn.meetingcost.CostCounterState.Ongoing
import pro.respawn.meetingcost.CostCounterState.Preparing
import pro.respawn.meetingcost.util.ClientNameForm
import pro.respawn.meetingcost.util.HourlyRateForm
import pro.respawn.meetingcost.util.InputSerializer
import pro.respawn.meetingcost.util.MemberCountForm
import pro.respawn.meetingcost.util.debuggable
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Serializable
data class Settings(
    val memberCount: Input = input(),
    val averageHourlyRate: Input = input(),
    val clientName: Input = input(),
) {

    val isValid = memberCount.isValid && averageHourlyRate.isValid && clientName.isValidOrEmpty
}

internal sealed interface CostCounterState : MVIState {

    val settings: Settings

    data class Error(
        val e: Exception?,
        override val settings: Settings,
    ) : CostCounterState

    @Serializable
    data class Preparing(
        override val settings: Settings = Settings(),
    ) : CostCounterState

    data class Ongoing(
        override val settings: Settings,
        val startedAt: Instant = Clock.System.now(),
        val elapsed: Duration = Duration.ZERO,
    ) : CostCounterState {

        val cost = settings.averageHourlyRate.value.toInt()
            .times(settings.memberCount.value.toInt())
            .times(elapsed.toDouble(DurationUnit.HOURS))
            .roundToInt()
    }

    data class DisplayingResult(
        override val settings: Settings,
        val cost: Int,
        val elapsed: Duration,
    ) : CostCounterState {

        val totalTime = elapsed * settings.memberCount.value.toInt()
    }
}

internal sealed interface CostCounterIntent : MVIIntent {

    // data class ToggledIncludeClient(val newValue: Boolean) : CostCounterIntent

    data class ChangedClientName(val value: String) : CostCounterIntent
    data class ChangedRate(val value: String) : CostCounterIntent
    data class ChangedMembers(val value: String) : CostCounterIntent
    data object ClickedSave : CostCounterIntent
}

internal sealed interface CostCounterAction : MVIAction

private typealias Ctx = PipelineContext<CostCounterState, CostCounterIntent, CostCounterAction>

internal class CostCounterContainer(

) : Container<CostCounterState, CostCounterIntent, CostCounterAction> {

    override val store = store(Preparing()) {
        configure {
            name = "CostCounter"
            debuggable = BuildFlags.debuggable
            stateStrategy = Atomic(reentrant = false)
            parallelIntents = true
        }
        recover {
            updateState { CostCounterState.Error(it, settings) }
            null
        }
        serializeState(
            path = ".cache",
            recover = NullRecover,
            serializer = Preparing.serializer(),
            behaviors = setOf(SaveBehavior.OnUnsubscribe(), SaveBehavior.Periodic())
        )
        whileSubscribed {
            while (isActive) {
                delay(1.seconds)
                updateState<Ongoing, _> {
                    copy(elapsed = Clock.System.now() - startedAt)
                }
            }
        }
        reduce { intent ->
            when (intent) {
                is CostCounterIntent.ChangedClientName -> updateStateImmediate<Preparing, _> {
                    copy(settings = settings.copy(clientName = intent.value.input()))
                }
                is CostCounterIntent.ChangedMembers -> updateStateImmediate<Preparing, _> {
                    copy(settings = settings.copy(memberCount = intent.value.input()))
                }
                is CostCounterIntent.ChangedRate -> updateStateImmediate<Preparing, _> {
                    copy(settings = settings.copy(averageHourlyRate = intent.value.input()))
                }
                is CostCounterIntent.ClickedSave -> updateState {
                    when (this) {
                        is DisplayingResult,
                        is CostCounterState.Error -> Preparing(settings)
                        is Ongoing -> if (cost > 0) DisplayingResult(settings, cost, elapsed) else Preparing(settings)
                        is Preparing -> {
                            val validated = settings.copy(
                                memberCount = MemberCountForm(settings.memberCount.value),
                                averageHourlyRate = HourlyRateForm(settings.averageHourlyRate.value),
                                clientName = ClientNameForm(settings.clientName.value)
                            )
                            if (validated.isValid) Ongoing(validated) else copy(settings = validated)
                        }
                    }
                }
            }
        }
    }
}

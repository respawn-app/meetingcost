@file:OptIn(ExperimentalTime::class)
@file:UseSerializers(InputSerializer::class)

package pro.respawn.meetingcost.ui.screens.costcounter

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.kmmutils.inputforms.Input
import pro.respawn.kmmutils.inputforms.dsl.input
import pro.respawn.kmmutils.inputforms.dsl.isValid
import pro.respawn.kmmutils.inputforms.dsl.isValidOrEmpty
import pro.respawn.meetingcost.analytics.AnalyticsEvent
import pro.respawn.meetingcost.util.InputSerializer
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
        override val settings: Settings,
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

internal sealed interface CostCounterIntent : MVIIntent, AnalyticsEvent {
    data class ChangedClientName(val name: String) : CostCounterIntent {

        override val event = "ChangedClientName"
    }

    data class ChangedMemberCount(val count: String) : CostCounterIntent
    data class ChangedAverageHourlyRate(val rate: String) : CostCounterIntent
    data object ClickedSave : CostCounterIntent {

        override val event = "ClickedSave"
    }
}

@file:OptIn(ExperimentalTime::class)

package pro.respawn.meetingcost.ui.screens.costcounter

import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.io.files.Path
import kotlinx.serialization.builtins.serializer
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.dsl.intent
import pro.respawn.flowmvi.dsl.store
import pro.respawn.flowmvi.dsl.updateState
import pro.respawn.flowmvi.dsl.updateStateImmediate
import pro.respawn.flowmvi.logging.PlatformStoreLogger
import pro.respawn.flowmvi.plugins.enableLogging
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce
import pro.respawn.flowmvi.plugins.whileSubscribed
import pro.respawn.flowmvi.savedstate.plugins.serializeState
import pro.respawn.kmmutils.inputforms.dsl.input
import pro.respawn.meetingcost.BuildFlags
import pro.respawn.meetingcost.analytics.analytics
import pro.respawn.meetingcost.system.FileManager
import pro.respawn.meetingcost.util.debuggable
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

private typealias Ctx = PipelineContext<CostCounterState, CostCounterIntent, Nothing>

internal class CostCounterContainer(
    private val fileManager: FileManager,
) : Container<CostCounterState, CostCounterIntent, Nothing> {

    override val store = store(initial = CostCounterState.Preparing(Settings())) {
        configure {
            name = "CostCounter"
            this.debuggable = BuildFlags.debuggable
            parallelIntents = true
            logger = PlatformStoreLogger
        }
        enableLogging()
        analytics()
        recover {
            updateState { CostCounterState.Error(it, this.settings) }
            null
        }
        serializeState(
            path = { Path(fileManager.cacheDir(), "cost_counter_state.json").toString() },
            serializer = CostCounterState.Preparing.serializer(),
        )
        whileSubscribed {
            while (coroutineContext.isActive) {
                updateState<CostCounterState.Ongoing, _> { copy(elapsed = Clock.System.now() - startedAt) }
                delay(1.seconds)
            }
        }
        reduce { intent ->
            when (intent) {
                is CostCounterIntent.ChangedAverageHourlyRate -> updateStateImmediate<CostCounterState.Preparing, _> {
                    copy(settings = settings.copy(averageHourlyRate = intent.rate.input()))
                }
                is CostCounterIntent.ChangedClientName -> updateStateImmediate<CostCounterState.Preparing, _> {
                    copy(settings = settings.copy(clientName = intent.name.input()))
                }
                is CostCounterIntent.ChangedMemberCount -> updateStateImmediate<CostCounterState.Preparing, _> {
                    copy(settings = settings.copy(memberCount = intent.count.input()))
                }
                is CostCounterIntent.ClickedSave -> updateState {
                    when (this) {
                        is CostCounterState.DisplayingResult,
                        is CostCounterState.Error -> CostCounterState.Preparing(settings)
                        is CostCounterState.Ongoing -> CostCounterState.DisplayingResult(
                            settings = settings,
                            cost = cost,
                            elapsed = elapsed
                        )
                        is CostCounterState.Preparing -> CostCounterState.Ongoing(
                            settings = settings,
                        )
                    }
                }
            }
        }
    }
}

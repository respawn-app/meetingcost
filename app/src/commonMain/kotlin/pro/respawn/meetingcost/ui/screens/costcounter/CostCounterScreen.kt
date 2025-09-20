package pro.respawn.meetingcost.ui.screens.costcounter

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.respawn.flowmvi.api.IntentReceiver
import pro.respawn.flowmvi.compose.dsl.subscribe
import pro.respawn.flowmvi.compose.preview.EmptyReceiver
import pro.respawn.kmmutils.inputforms.dsl.isEmpty
import pro.respawn.kmmutils.inputforms.dsl.isValid
import pro.respawn.meetingcost.ui.icons.Done
import pro.respawn.meetingcost.ui.icons.Icons
import pro.respawn.meetingcost.ui.icons.PlayArrow
import pro.respawn.meetingcost.ui.icons.Refresh
import pro.respawn.meetingcost.ui.theme.Opacity
import pro.respawn.meetingcost.ui.widgets.RIcon
import pro.respawn.meetingcost.ui.widgets.RTextInput
import pro.respawn.meetingcost.ui.widgets.SlidingNumber
import pro.respawn.meetingcost.ui.widgets.TypeCrossfade
import pro.respawn.meetingcost.util.formatAsTime

@Composable
internal fun CostCounterScreen(
) {
    val container = remember { CostCounterContainer() }
    with(container.store) {
        LaunchedEffect(Unit) {
            container.store.start(this).awaitUntilClosed()
        }
        val state by subscribe()
        CostCounterScreenContent(state)
    }
}

@Composable
private fun IntentReceiver<CostCounterIntent>.CostCounterScreenContent(
    state: CostCounterState,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.Center,
    floatingActionButton = {
        FloatingActionButton(
            onClick = { intent(CostCounterIntent.ClickedSave) },
        ) {
            val icon = when (state) {
                is CostCounterState.DisplayingResult,
                is CostCounterState.Error -> Icons.Refresh
                is CostCounterState.Ongoing -> Icons.Done
                is CostCounterState.Preparing -> Icons.PlayArrow
            }
            RIcon(icon)
        }
    }
) {
    TypeCrossfade(state) {
        when (this) {
            is CostCounterState.Error -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp).heightIn(max = 400.dp).verticalScroll(rememberScrollState())
            ) {
                Text("Error:\n${e?.stackTraceToString()}")
            }
            is CostCounterState.DisplayingResult -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp)
            ) {
                Text("This meeting cost")
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "$ $cost",
                    style = MaterialTheme.typography.displayLarge,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text("${settings.memberCount.value} people spent ${totalTime.inWholeMinutes} minutes total")
            }
            is CostCounterState.Preparing -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp).padding(horizontal = 16.dp)
            ) {
                RTextInput(
                    input = settings.averageHourlyRate,
                    onTextChange = { intent(CostCounterIntent.ChangedAverageHourlyRate(it)) },
                    label = "What's the average hourly rate?"
                )
                Spacer(modifier = Modifier.height(32.dp))
                RTextInput(
                    input = settings.memberCount,
                    onTextChange = { intent(CostCounterIntent.ChangedMemberCount(it)) },
                    label = "How many members does this meeting have?"
                )
                Spacer(modifier = Modifier.height(32.dp))
                RTextInput(
                    input = settings.clientName,
                    onTextChange = { intent(CostCounterIntent.ChangedClientName(it)) },
                    label = "Who pays for this meeting?"
                )
                Spacer(modifier = Modifier.height(32.dp))
                Spacer(modifier = Modifier.height(32.dp))
            }
            is CostCounterState.Ongoing -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(max = 600.dp).padding(horizontal = 16.dp),
            ) {
                Spacer(Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.animateContentSize(),
                ) {
                    Text(
                        text = if (settings.clientName.isEmpty)
                            "This meeting has cost"
                        else
                            "${settings.clientName.value.capitalize(Locale.current)} has paid"
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        val style = MaterialTheme.typography.displayLarge
                        Text("$ ", style = style)
                        SlidingNumber(cost, textStyle = style)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    if (settings.clientName.isValid) {
                        Text("for this meeting")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Text(
                        text = "(lasted for ${elapsed.formatAsTime(true)})",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.alpha(Opacity.secondary).animateContentSize()
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@Preview
private fun CostCounterScreenPreview() = EmptyReceiver {
    CostCounterScreenContent(CostCounterState.Preparing(Settings()))
}

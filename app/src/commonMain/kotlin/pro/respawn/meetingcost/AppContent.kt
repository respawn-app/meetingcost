package pro.respawn.meetingcost

import androidx.compose.runtime.Composable
import pro.respawn.meetingcost.ui.screens.costcounter.CostCounterScreen
import pro.respawn.meetingcost.ui.theme.RespawnTheme

@Composable
fun AppContent() = RespawnTheme {
    CostCounterScreen()
}

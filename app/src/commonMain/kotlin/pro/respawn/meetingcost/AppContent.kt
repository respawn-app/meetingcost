package pro.respawn.meetingcost

import androidx.compose.runtime.Composable
import org.kodein.di.DI
import org.kodein.di.compose.withDI
import org.kodein.di.conf.global
import pro.respawn.meetingcost.ui.theme.RespawnTheme

@Composable
fun AppContent() = RespawnTheme {
    withDI(DI.global) { CostCounterScreen() }
}

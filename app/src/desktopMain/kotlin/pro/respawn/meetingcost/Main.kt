package pro.respawn.meetingcost

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import pro.respawn.meetingcost.di.initializeDi

fun main() = application {
    initializeDi()
    val state = rememberWindowState(
        width = 600.dp,
        height = 600.dp,
        position = WindowPosition.Aligned(Alignment.Center),
    )
    Window(
        onCloseRequest = ::exitApplication,
        // icon = painterResource(Res.drawable.ic_flowmvi_32),
        title = "Meeting Cost",
        state = state,
    ) {
        AppContent()
    }
}

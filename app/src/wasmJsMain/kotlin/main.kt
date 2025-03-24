@file:Suppress("MissingPackageDeclaration", "Filename")

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import pro.respawn.meetingcost.AppContent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) { AppContent() }
}

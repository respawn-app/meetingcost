@file:Suppress("MissingPackageDeclaration", "Filename")

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import pro.respawn.meetingcost.AppContent
import pro.respawn.meetingcost.di.initializeDi

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initializeDi()
    ComposeViewport(document.body!!) { AppContent() }
}

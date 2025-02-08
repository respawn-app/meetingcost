package pro.respawn.meetingcost.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pro.respawn.meetingcost.ui.theme.DarkColors
import pro.respawn.meetingcost.ui.theme.LightColors

@Composable
internal actual fun rememberColorScheme(
    dark: Boolean,
    dynamic: Boolean,
) = remember(dark) { if (dark) DarkColors else LightColors }

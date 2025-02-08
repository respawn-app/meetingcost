package pro.respawn.meetingcost.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun rememberColorScheme(dark: Boolean, dynamic: Boolean): ColorScheme = if (dark) DarkColors else LightColors

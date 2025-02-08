@file:Suppress("DuplicatedCode")

package pro.respawn.meetingcost.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable

private val shapes = Shapes(
    extraSmall = RoundedCornerShape(CornerRadius.extraSmall),
    small = RoundedCornerShape(CornerRadius.small),
    medium = RoundedCornerShape(CornerRadius.medium),
    large = RoundedCornerShape(CornerRadius.large),
    extraLarge = RoundedCornerShape(CornerRadius.extraLarge),
)

@Composable
internal expect fun rememberColorScheme(dark: Boolean, dynamic: Boolean): ColorScheme

@Composable
fun RespawnTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = rememberColorScheme(dark = useDarkTheme, dynamic = useDynamicColors),
        shapes = shapes,
        typography = rememberAppTypography(),
        content = content,
    )
}

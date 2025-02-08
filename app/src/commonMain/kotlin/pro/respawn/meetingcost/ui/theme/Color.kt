package pro.respawn.meetingcost.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Respawn's color palette, in order of prominence
val mint = Color(0xff00d46a)
val skyblue = Color(0xff0288d1)
val bright_red = Color(0xFFFF5555)
val soft_yellow = Color(0xffffeb3b)

val raisin_black = Color(0xFF121212)
val imperial_red = Color(0xffF71735)

val skyblue_darker = Color(0xff005b9f)
val pink_darker = Color(0xffc94781)
val soft_yellow_darker = Color(0xFFDFCE03)

val mint_lighter = Color(0xff66ffa5)
val mint_darker = Color(0xff00b147)
val skyblue_lighter = Color(0xff5eb8ff)
val pink_lighter = Color(0xffff79b0)
val soft_yellow_lighter = Color(0xffffff72)
val error = Color(0xFFF71735)

// region light
internal val md_theme_light_primary = Color(0xFF00722E)
internal val md_theme_light_onPrimary = Color(0xFFE2FFEB)
internal val md_theme_light_primaryContainer = Color(0xFF51CF79)
internal val md_theme_light_onPrimaryContainer = Color(0xFF00210B)
internal val md_theme_light_secondary = Color(0xFF00639A)
internal val md_theme_light_onSecondary = Color(0xFFE6F6FF)
internal val md_theme_light_secondaryContainer = Color(0xFFCEE5FF)
internal val md_theme_light_onSecondaryContainer = Color(0xFF001D32)
internal val md_theme_light_tertiary = soft_yellow_darker
internal val md_theme_light_onTertiary = Color(0xFF1D1B07)
internal val md_theme_light_tertiaryContainer = Color(0xFFFFF38D)
internal val md_theme_light_onTertiaryContainer = Color(0xFF201C00)
internal val md_theme_light_error = bright_red
internal val md_theme_light_onError = Color(0xFFFFFFFF)
internal val md_theme_light_errorContainer = Color(0xFFFFDAD7)
internal val md_theme_light_onErrorContainer = Color(0xFF410006)
internal val md_theme_light_background = Color(0xFFFFFFFF)
internal val md_theme_light_onBackground = Color(0xFF00210E)
internal val md_theme_light_surface = Color(0xFFF7F7F7)
internal val md_theme_light_surfaceBright = Color(0xFFF9F9F9)
internal val md_theme_light_surfaceDim = Color(0xFFF0F0F0)
internal val md_theme_light_onSurface = Color(0xFF00210E)
internal val md_theme_light_surfaceVariant = Color(0xFFF6F6F6)
internal val md_theme_light_onSurfaceVariant = Color(0xFF414941)
internal val md_theme_light_outline = Color(0xFF717970)
internal val md_theme_light_inverseOnSurface = Color(0xFFFCFFFD)
internal val md_theme_light_inverseSurface = Color(0xFF212E27)
internal val md_theme_light_inversePrimary = Color(0xFF2CE377)
internal val md_theme_light_shadow = Color(0xFF000000)
internal val md_theme_light_surfaceTint = Color(0xFFE2FFF1)
internal val md_theme_light_outlineVariant = Color(0xFFD1D9CF)
internal val md_theme_light_scrim = Color(0xFFFFFFFF)
internal val md_theme_light_surfaceContainerLowest = Color(0xFFF8F8F8)
internal val md_theme_light_surfaceContainerLow = Color(0xFFF7F7F7)
internal val md_theme_light_surfaceContainer = Color(0xFFF5F5F5)
internal val md_theme_light_surfaceContainerHigh = Color(0xFFF3F3F3)
internal val md_theme_light_surfaceContainerHighest = Color(0xFFF1F1F1)

//endregion
// region dark
internal val md_theme_dark_primary = mint
internal val md_theme_dark_onPrimary = Color(0xFF003918)
internal val md_theme_dark_primaryContainer = Color(0xFF005225)
internal val md_theme_dark_onPrimaryContainer = Color(0xFF63FF94)
internal val md_theme_dark_secondary = Color(0xFF96CCFF)
internal val md_theme_dark_onSecondary = Color(0xFF003353)
internal val md_theme_dark_secondaryContainer = Color(0xFF004A75)
internal val md_theme_dark_onSecondaryContainer = Color(0xFFCEE5FF)
internal val md_theme_dark_tertiary = soft_yellow
internal val md_theme_dark_onTertiary = Color(0xFF363100)
internal val md_theme_dark_tertiaryContainer = Color(0xFF4F4800)
internal val md_theme_dark_onTertiaryContainer = Color(0xFFF9E534)
internal val md_theme_dark_error = bright_red
internal val md_theme_dark_errorContainer = Color(0xFF93000A)
internal val md_theme_dark_onError = Color(0xFFFFDCDC)
internal val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
internal val md_theme_dark_background = Color(0xFF0F110E)
internal val md_theme_dark_onBackground = Color(0xFFE2E3DE)
internal val md_theme_dark_surface = Color(0xFF161916)
internal val md_theme_dark_onSurface = Color(0xFFE2E3DE)
internal val md_theme_dark_surfaceVariant = Color(0xFF262825)
internal val md_theme_dark_onSurfaceVariant = Color(0xFFC1C9BE)
internal val md_theme_dark_outline = Color(0xFF8B9389)
internal val md_theme_dark_inverseOnSurface = Color(0xFF00210E)
internal val md_theme_dark_inverseSurface = Color(0xFF99F7B5)
internal val md_theme_dark_inversePrimary = mint_darker
internal val md_theme_dark_shadow = Color(0xFF000000)
internal val md_theme_dark_surfaceTint = Color(0xFF41E484)
internal val md_theme_dark_outlineVariant = Color(0xFF414941)
internal val md_theme_dark_scrim = Color(0xFF000000)
internal val md_theme_dark_surfaceContainerLowest = Color(0xFF151714)
internal val md_theme_dark_surfaceContainerLow = Color(0xFF1A1C19)
internal val md_theme_dark_surfaceContainer = Color(0xFF1F211E)
internal val md_theme_dark_surfaceContainerHigh = Color(0xFF242623)
internal val md_theme_dark_surfaceContainerHighest = Color(0xFF292B28)
internal val md_theme_dark_surfaceBright = Color(0xFF292B28)
internal val md_theme_dark_surfaceDim = Color(0xFF0D0F0D)
// endregion

internal val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
    surfaceBright = md_theme_light_surfaceBright,
    surfaceContainer = md_theme_light_surfaceContainer,
    surfaceContainerHigh = md_theme_light_surfaceContainerHigh,
    surfaceContainerHighest = md_theme_light_surfaceContainerHighest,
    surfaceContainerLow = md_theme_light_surfaceContainerLow,
    surfaceContainerLowest = md_theme_light_surfaceContainerLowest,
    surfaceDim = md_theme_light_surfaceDim,
)

internal val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
    surfaceBright = md_theme_dark_surfaceBright,
    surfaceContainer = md_theme_dark_surfaceContainer,
    surfaceContainerHigh = md_theme_dark_surfaceContainerHigh,
    surfaceContainerHighest = md_theme_dark_surfaceContainerHighest,
    surfaceContainerLow = md_theme_dark_surfaceContainerLow,
    surfaceContainerLowest = md_theme_dark_surfaceContainerLowest,
    surfaceDim = md_theme_dark_surfaceDim,
)

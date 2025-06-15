package org.haidrrrry.auth.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import org.haidrrrry.auth.classes.AppTheme
import org.haidrrrry.auth.utils.AppColors
import org.haidrrrry.auth.utils.ThemeManager
import org.haidrrrry.auth.utils.ThemeMode

@Composable
fun ProvideAppTheme(
    themeManager: ThemeManager,
    content: @Composable () -> Unit
) {
    val themeMode by themeManager.themeMode.collectAsState()
    val isSystemDark = isSystemInDarkTheme()

    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemDark
    }

    val theme = if (isDarkTheme) {
        AppTheme(
            primary = AppColors.Dark.Primary,
            primaryVariant = AppColors.Dark.PrimaryVariant,
            primaryLight = AppColors.Dark.PrimaryLight,
            background = AppColors.Dark.Background,
            backgroundSecondary = AppColors.Dark.BackgroundSecondary,
            surface = AppColors.Dark.Surface,
            surfaceVariant = AppColors.Dark.SurfaceVariant,
            onSurface = AppColors.Dark.OnSurface,
            onSurfaceVariant = AppColors.Dark.OnSurfaceVariant,
            onPrimary = AppColors.Dark.OnPrimary,
            cardBackground = AppColors.Dark.CardBackground,
            cardElevated = AppColors.Dark.CardElevated,
            success = AppColors.Dark.Success,
            warning = AppColors.Dark.Warning,
            error = AppColors.Dark.Error,
            timerGood = AppColors.Dark.TimerGood,
            timerWarning = AppColors.Dark.TimerWarning,
            timerCritical = AppColors.Dark.TimerCritical,
            isLight = false
        )
    } else {
        AppTheme(
            primary = AppColors.Light.Primary,
            primaryVariant = AppColors.Light.PrimaryVariant,
            primaryLight = AppColors.Light.PrimaryLight,
            background = AppColors.Light.Background,
            backgroundSecondary = AppColors.Light.BackgroundSecondary,
            surface = AppColors.Light.Surface,
            surfaceVariant = AppColors.Light.SurfaceVariant,
            onSurface = AppColors.Light.OnSurface,
            onSurfaceVariant = AppColors.Light.OnSurfaceVariant,
            onPrimary = AppColors.Light.OnPrimary,
            cardBackground = AppColors.Light.CardBackground,
            cardElevated = AppColors.Light.CardElevated,
            success = AppColors.Light.Success,
            warning = AppColors.Light.Warning,
            error = AppColors.Light.Error,
            timerGood = AppColors.Light.TimerGood,
            timerWarning = AppColors.Light.TimerWarning,
            timerCritical = AppColors.Light.TimerCritical,
            isLight = true
        )
    }

    CompositionLocalProvider(
        LocalAppTheme provides theme,
        content = content
    )
}
// Composition Local
val LocalAppTheme = compositionLocalOf<AppTheme> {
    error("No AppTheme provided")
}
@Composable
fun appTheme(): AppTheme = LocalAppTheme.current
package com.sam.gogozoo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.sam.gogoidea.R
import com.sam.gogoidea.ui.theme.MyTypography

@Composable
private fun myLightColorScheme() = lightColorScheme(
    primary = colorResource(id = R.color.primary_color),
    secondary = colorResource(id = R.color.secondary_color),
    background = colorResource(id = R.color.bg_primary),
    onPrimary = colorResource(id = R.color.text_on_color),
    onSecondary = colorResource(id = R.color.text_on_color),
    onBackground = colorResource(id = R.color.text_on_bg),
    onSurface = colorResource(id = R.color.text_on_bg),
)

@Composable
private fun myDarkColorScheme() = darkColorScheme(
    primary = colorResource(id = R.color.primary_color),
    secondary = colorResource(id = R.color.secondary_color),
    background = colorResource(id = R.color.bg_primary),
    onPrimary = colorResource(id = R.color.text_on_color),
    onSecondary = colorResource(id = R.color.text_on_color),
    onBackground = colorResource(id = R.color.text_on_bg),
    onSurface = colorResource(id = R.color.text_on_bg),
)

@Composable
fun IdeaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> myDarkColorScheme()
        else -> myLightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyTypography,
        content = content
    )
}

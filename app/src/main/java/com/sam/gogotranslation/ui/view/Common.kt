package com.sam.gogotranslation.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit) =
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }

@Composable
fun Modifier.noBoundClickable(onClick: () -> Unit) =
    this.clickable(
        indication = ripple(bounded = false),
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick()
    }

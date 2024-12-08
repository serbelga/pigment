package dev.sergiobelda.pigment

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 */
@Immutable
data class ColorItem(
    val color: Color?,
    val enabled: Boolean = true
)

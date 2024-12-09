package dev.sergiobelda.pigment.samples

import androidx.compose.ui.graphics.Color
import dev.sergiobelda.pigment.ColorItem

internal val colors = listOf(
    null,
    Color(0xFF000000),
    Color(0xFFFFFFFF),
    Color(0xFFFAFAFA),
    Color(0x80FF4444),
    Color(0xFFEF5350),
    Color(0xFFEC407A),
    Color(0xFFAB47BC),
    Color(0xFF7E57C2),
    Color(0xFF5C6BC0),
    Color(0xFF42A5F5),
    Color(0xFF29B6F6),
    Color(0xFF26C6DA),
    Color(0xFF26A69A),
    Color(0xFF66BB6A),
    Color(0xFF9CCC65),
    Color(0xFFD4E157),
    Color(0xFFFFEE58),
    Color(0xFFFFCA28),
    Color(0xFFFFA726),
    Color(0xFFFF7043),
)

internal val colorItems = colors.map {
    ColorItem(
        color = it,
        enabled = true
    )
}

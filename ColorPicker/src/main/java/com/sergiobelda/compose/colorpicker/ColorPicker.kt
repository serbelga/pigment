package com.sergiobelda.compose.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayout::class)
@Composable
fun ColorPicker(
    items: List<Color>,
    selectedColor: Color,
    onColorSelected: (color: Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        FlowRow(
            mainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSize = SizeMode.Expand,
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp
        ) {
            items.distinct().forEach { color ->
                ColorItem(
                    selected = color == selectedColor,
                    color = color,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    selected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .preferredSize(40.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                modifier = Modifier.align(Alignment.Center),
                tint = Color.White
            )
        }
    }
}
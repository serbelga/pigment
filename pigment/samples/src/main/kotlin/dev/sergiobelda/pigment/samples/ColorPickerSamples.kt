package dev.sergiobelda.pigment.samples

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import dev.sergiobelda.pigment.ColorPicker

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun ColorPickerFlowRowSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.FlowRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected
    )
}

@Preview
@Composable
fun ColorPickerLazyRowSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.LazyRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected
    )
}

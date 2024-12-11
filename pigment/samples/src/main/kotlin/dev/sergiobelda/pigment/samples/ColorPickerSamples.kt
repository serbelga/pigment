package dev.sergiobelda.pigment.samples

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import dev.sergiobelda.pigment.ColorPicker
import dev.sergiobelda.pigment.ColorPickerSize

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

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun ColorPickerFlowRowMediumSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.FlowRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = ColorPickerSize.Medium
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun ColorPickerFlowRowLargeSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.FlowRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = ColorPickerSize.Large
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

@Preview
@Composable
fun ColorPickerLazyRowMediumSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.LazyRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = ColorPickerSize.Medium
    )
}

@Preview
@Composable
fun ColorPickerLazyRowLargeSample() {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.LazyRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = ColorPickerSize.Large
    )
}

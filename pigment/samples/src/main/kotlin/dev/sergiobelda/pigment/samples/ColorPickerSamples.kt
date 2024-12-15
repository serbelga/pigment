/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.pigment.samples

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
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
        onColorSelected = onColorSelected,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun ColorPickerFlowRowSizeSample(
    @PreviewParameter(ColorPickerSizeProvider::class) size: ColorPickerSize,
) {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.FlowRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = size,
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
        onColorSelected = onColorSelected,
    )
}

@Preview
@Composable
fun ColorPickerLazyRowSizeSample(
    @PreviewParameter(ColorPickerSizeProvider::class) size: ColorPickerSize,
) {
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorItems.firstOrNull()?.color)
    }
    ColorPicker.LazyRow(
        colors = colorItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = size,
    )
}

/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.pigment.samples.colorpicker

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.sergiobelda.pigment.colorpicker.ColorPicker
import dev.sergiobelda.pigment.colorpicker.ColorPickerDefaults
import dev.sergiobelda.pigment.colorpicker.ColorPickerSize

@Preview
@Composable
private fun ColorPickerFlowRowPreview() {
    ColorPickerFlowRowSample()
}

@Preview
@Composable
private fun ColorPickerFlowRowMapPreview() {
    ColorPickerFlowRowMapSample()
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
private fun ColorPickerFlowRowSizePreview(
    @PreviewParameter(ColorPickerSizeProvider::class) size: ColorPickerSize,
) {
    val colorPickerItems = remember { colorPickerItems.toMutableStateList() }
    val (selectedColor, onColorSelected) =
        remember {
            mutableStateOf(Color.Unspecified)
        }
    ColorPicker.FlowRow(
        colors = colorPickerItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        size = size,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
private fun ColorPickerFlowRowCustomPreview() {
    val colorPickerItems = remember { colorPickerItems.toMutableStateList() }
    val (selectedColor, onColorSelected) =
        remember {
            mutableStateOf(Color.Unspecified)
        }
    ColorPicker.FlowRow(
        colors = colorPickerItems,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected,
        shape = RoundedCornerShape(12.dp),
        size = ColorPickerSize.Small,
        colorIndicatorColors =
            ColorPickerDefaults.colorIndicatorColors(
                onDarkColor = Color.LightGray,
                onLightColor = Color.Blue,
            ),
    )
}

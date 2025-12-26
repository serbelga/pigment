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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import dev.sergiobelda.pigment.colorpicker.ColorPicker
import dev.sergiobelda.pigment.colorpicker.ColorPickerItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerFlowRowSample() {
    val colorPickerItems = remember { colorPickerItems.toMutableStateList() }
    val (selectedColor, onColorSelected) =
        remember {
            mutableStateOf(Color.Unspecified)
        }
    Column {
        ColorPicker.FlowRow(
            colors = colorPickerItems,
            selectedColor = selectedColor,
            onColorSelected = onColorSelected,
        )
        ColorVisualizer(color = selectedColor)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerFlowRowMapSample() {
    val colorMap =
        remember {
            mutableStateMapOf(
                "Red" to ColorPickerItem(Color.Red),
                "Blue" to ColorPickerItem(Color.Blue),
            )
        }
    val (selectedItem, onItemSelected) =
        remember {
            mutableStateOf("Red")
        }
    Column {
        ColorPicker.FlowRow(
            colorsMap = colorMap,
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
        )
        BasicText(text = selectedItem)
    }
}

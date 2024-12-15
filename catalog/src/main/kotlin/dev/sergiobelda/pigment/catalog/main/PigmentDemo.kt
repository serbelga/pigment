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

package dev.sergiobelda.pigment.catalog.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.pigment.catalog.R
import dev.sergiobelda.pigment.colorpicker.ColorPicker
import dev.sergiobelda.pigment.colorpicker.ColorPickerItem
import dev.sergiobelda.pigment.samples.colorpicker.colorPickerItems
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun PigmentDemo() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }

    val colorPickerItems = remember { colorPickerItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember {
        mutableStateOf(colorPickerItems.first().color)
    }

    BottomSheetScaffold(
        sheetContent = {
            ColorPickerBottomSheet(
                colors = colorPickerItems,
                selectedColor = selectedColor,
                onColorSelected = onColorSelected,
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                ) {
                    Text(stringResource(R.string.open_bottomsheet))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { dialogState.value = true },
                ) {
                    Text(stringResource(R.string.open_dialog))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(selectedColor)
                    },
            ) {
                ColorPicker.LazyRow(
                    colors = colorPickerItems,
                    selectedColor = selectedColor,
                    onColorSelected = onColorSelected,
                )
            }
        }
        if (dialogState.value) {
            ColorPickerDialog(
                colors = colorPickerItems,
                selectedColor = selectedColor,
                onColorSelected = onColorSelected,
                onDismissRequest = { dialogState.value = false },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ColorPickerDialog(
    colors: List<ColorPickerItem>,
    selectedColor: Color,
    onColorSelected: (color: Color) -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.select_color),
                style = MaterialTheme.typography.bodySmall,
            )
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                ColorPicker.FlowRow(
                    colors = colors,
                    selectedColor = selectedColor,
                    onColorSelected = onColorSelected,
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(R.string.ok))
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ColorPickerBottomSheet(
    colors: List<ColorPickerItem>,
    selectedColor: Color,
    onColorSelected: (color: Color) -> Unit,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(R.string.select_color),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 16.dp),
        )
        HorizontalDivider()
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            ColorPicker.FlowRow(
                colors = colors,
                selectedColor = selectedColor,
                onColorSelected = onColorSelected,
            )
        }
    }
}

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.pigment.samples.colorpicker.ColorPickerFlowRowSample
import dev.sergiobelda.pigment.samples.colorpicker.ColorPickerLazyRowSample

@Composable
internal fun PigmentDemo() {
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .padding(paddingValues),
        ) {
            ColorPickerFlowRowDemo()
            ColorPickerLazyRowDemo()
        }
    }
}

@Composable
private fun ColorPickerFlowRowDemo() {
    Column {
        Text(
            "ColorPicker.FlowRow",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
        ColorPickerFlowRowSample()
    }
}

@Composable
private fun ColorPickerLazyRowDemo() {
    Column {
        Text(
            "ColorPicker.LazyRow",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
        ColorPickerLazyRowSample()
    }
}

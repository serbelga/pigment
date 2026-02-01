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

package dev.sergiobelda.pigment.colorpicker

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import dev.sergiobelda.pigment.sample.colorPickerItems
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ColorPickerTest {
    @get:Rule
    val paparazzi =
        Paparazzi(
            deviceConfig = DeviceConfig.PIXEL_5,
            renderingMode = SessionParams.RenderingMode.SHRINK,
            showSystemUi = false,
        )

    @Test
    fun FlowRow(
        @TestParameter size: ColorPickerSize,
    ) {
        snapshot {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                ColorPicker.FlowRow(
                    colors = colorPickerItems,
                    selectedColor = Color.Unspecified,
                    onColorSelected = {},
                    size = size,
                )
            }
        }
    }

    @Test
    fun FlowRow(
        @TestParameter color: ColorTestCase,
    ) {
        snapshot {
            ColorPicker.FlowRow(
                colors = colorPickerItems,
                selectedColor = color.value,
                onColorSelected = {},
            )
        }
    }

    @Test
    fun LazyRow(
        @TestParameter size: ColorPickerSize,
    ) {
        snapshot {
            ColorPicker.LazyRow(
                colors = colorPickerItems,
                selectedColor = Color.Unspecified,
                onColorSelected = {},
                size = size,
            )
        }
    }

    // Workaround to keep using CompositionLocalProvider until
    // https://youtrack.jetbrains.com/issue/CMP-7276/IllegalStateException-using-Compose-Components-Resources-and-Paparazzi
    // is resolved.
    private fun snapshot(content: @Composable () -> Unit) {
        paparazzi.snapshot {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
                content = content,
            )
        }
    }
}

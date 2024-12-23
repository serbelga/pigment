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

import androidx.compose.ui.graphics.Color

enum class ColorTestCase(val value: Color) {
    Unspecified(Color.Unspecified),
    White(Color.White),
    Black(Color.Black),
    LightColor(Color(0xFFFFEE58)),
    DarkColor(Color(0xFFEC407A)),
}

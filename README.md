# Pigment

[![Maven Central](https://img.shields.io/maven-central/v/dev.sergiobelda.pigment/pigment)](https://search.maven.org/search?q=g:dev.sergiobelda.pigment)

🎨 A color selector for Jetpack Compose

```gradle
dependencies {
    implementation 'dev.sergiobelda.pigment:pigment:$VERSION'
}
```

## Usage

### FlowRow

<img src="./screenshots/ColorPicker_FlowRow.png" width="240" />

```kotlin
val colorPickerItems = remember {
    listOf(
        ColorPickerItem(color = null),
        ColorPickerItem(color = Color.Black),
        ColorPickerItem(color = Color(0x80FF4444)),
        ColorPickerItem(color = Color(0xFFEF5350), enabled = false),
    ).toMutableStateList()
}
val (selectedColor, onColorSelected) = remember {
    mutableStateOf(colorPickerItems.firstOrNull()?.color)
}
ColorPicker.FlowRow(
    colors = colorPickerItems,
    selectedColor = selectedColor,
    onColorSelected = onColorSelected,
)
```

### LazyRow

<img src="./screenshots/ColorPicker_LazyRow.png" width="240" />

```kotlin
val colorPickerItems = remember {
    listOf(
        ColorPickerItem(color = null),
        ColorPickerItem(color = Color.Black),
        ColorPickerItem(color = Color(0x80FF4444)),
        ColorPickerItem(color = Color(0xFFEF5350), enabled = false),
    ).toMutableStateList()
}
val (selectedColor, onColorSelected) = remember {
    mutableStateOf(colorPickerItems.firstOrNull()?.color)
}
ColorPicker.LazyRow(
    colors = colorPickerItems,
    selectedColor = selectedColor,
    onColorSelected = onColorSelected,
)
```

-------------------

## License

```
   Copyright 2024 Sergio Belda

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

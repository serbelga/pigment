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

## Screenshots

### BottomSheet

<img src="./screenshots/sheet.png" width=240 />

```kotlin
BottomSheetScaffold(
    sheetContent = {
        Column {
            Text(
                "Select color",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(12.dp)
            )
            Divider(thickness = 1.dp, color = MaterialTheme.colors.onPrimary)
            ColorPicker(
                colors,
                selectedColor,
                onColorSelected,
                modifier = Modifier.padding(12.dp)
            )
        }
    },
    scaffoldState = scaffoldState,
    sheetPeekHeight = 0.dp
) {
    ...
}
```

### Dialog

<img src="./screenshots/dialog.png" width=240 />

```kotlin
AlertDialog(
    onDismissRequest = { dialogState.value = false },
    title = {
        Text(
            "Select color",
            style = MaterialTheme.typography.body1
        )
    },
    text = {
        ColorPicker(
            colors,
            selectedColor,
            onColorSelected
        )
    },
    confirmButton = {
        Button(onClick = { dialogState.value = false }) {
            Text("OK")
        }
    }
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

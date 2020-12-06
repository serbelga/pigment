# ComposeColorPicker
🎨 ColorPicker for Jetpack Compose

```gradle
dependencies {
    implementation 'com.sergiobelga.colorpicker:colorpicker:1.0.0-dev02'
}
```

## Usage

```kotlin
val colors = listOf(
    Color(0xFFEF5350),
    Color(0xFFEC407A),
    Color(0xFFAB47BC)
)
val (selectedColor, onColorSelected) = remember { mutableStateOf(colors[0]) }

ColorPicker(
    colors,
    selectedColor,
    onColorSelected
)
```

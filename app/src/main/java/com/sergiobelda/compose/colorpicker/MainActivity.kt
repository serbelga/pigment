package com.sergiobelda.compose.colorpicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.sergiobelda.compose.colorpicker.ui.ColorPickerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorPickerTheme {
                BottomSheet()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun BottomSheet() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val colors = listOf(
        Color(0xFFEF5350),
        Color(0xFFEC407A),
        Color(0xFFAB47BC),
        Color(0xFF7E57C2),
        Color(0xFF5C6BC0),
        Color(0xFF42A5F5),
        Color(0xFF29B6F6),
        Color(0xFF26C6DA),
        Color(0xFF26A69A),
        Color(0xFF66BB6A),
        Color(0xFF9CCC65),
        Color(0xFFD4E157),
        Color(0xFFFFEE58),
        Color(0xFFFFCA28),
        Color(0xFFFFA726),
        Color(0xFFFF7043)
    )
    val (selectedColor, onColorSelected) = remember { mutableStateOf(colors[0]) }
    BottomSheetScaffold(
        sheetContent = {
            Column {
                Text(
                    "Select color",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(12.dp)
                )
                Divider(thickness = 1.dp, color = Color.Black)
                ColorPicker(
                    colors,
                    selectedColor,
                    onColorSelected,
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(selectedColor)
        ) {
            Button(
                onClick = { scaffoldState.bottomSheetState.expand() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Select color")
            }
        }
    }
}

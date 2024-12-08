package dev.sergiobelda.pigment.catalog.main

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.sergiobelda.pigment.ColorPicker
import dev.sergiobelda.pigment.catalog.R
import dev.sergiobelda.pigment.catalog.colorItems
import dev.sergiobelda.pigment.catalog.colors
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
internal fun PigmentDemo() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }
    val colorItems = remember { colorItems.toMutableStateList() }
    val (selectedColor, onColorSelected) = remember { mutableStateOf(colors[0]) }
    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier.navigationBarsPadding(),
            ) {
                Text(
                    text = stringResource(R.string.select_color),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(12.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)
                ColorPicker.FlowRow(
                    colorItems.toImmutableList(),
                    selectedColor,
                    onColorSelected,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
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
                    .padding(vertical = 12.dp)
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
                        selectedColor?.let {
                            drawRect(it)
                        }
                    }
            ) {
                ColorPicker.LazyRow(
                    colors = colorItems.toImmutableList(),
                    selectedColor = selectedColor,
                    onColorSelected = onColorSelected,
                )
            }
        }
        if (dialogState.value) {
            AlertDialog(
                onDismissRequest = { dialogState.value = false },
                title = {
                    Text(
                        text = stringResource(R.string.select_color),
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPicker.FlowRow(
                            colors = colorItems.toImmutableList(),
                            selectedColor = selectedColor,
                            onColorSelected = onColorSelected,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { dialogState.value = false }) {
                        Text(stringResource(R.string.ok))
                    }
                },
            )
        }
    }
}

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

package dev.sergiobelda.pigment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 */
object ColorPicker {

    /**
     */
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FlowRow(
        colors: List<ColorItem>,
        selectedColor: Color?,
        onColorSelected: (color: Color?) -> Unit,
        modifier: Modifier = Modifier,
        colorItemBorderWidth: ColorIndicatorBorderWidth = ColorPickerDefaults.colorIndicatorBorderWidth(),
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        maxItemsInEachRow: Int = Int.MAX_VALUE,
        maxLines: Int = Int.MAX_VALUE,
        overflow: FlowRowOverflow = FlowRowOverflow.Clip,
    ) {
        FlowRow(
            modifier = modifier.semantics {
                collectionInfo = CollectionInfo(
                    rowCount = maxLines,
                    columnCount = colors.size.coerceAtMost(maxItemsInEachRow)
                )
            },
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow,
            maxLines = maxLines,
            overflow = overflow
        ) {
            colors.forEach { colorItem ->
                // TODO: Add Modifier.semantics { collectionItemInfo }
                ColorItem(
                    colorItem = colorItem,
                    selected = colorItem.color == selectedColor,
                    onClick = { onColorSelected(colorItem.color) },
                    colorItemBorderWidth = colorItemBorderWidth,
                )
            }
        }
    }

    /**
     */
    @Composable
    fun LazyRow(
        colors: List<ColorItem>,
        selectedColor: Color?,
        onColorSelected: (color: Color?) -> Unit,
        modifier: Modifier = Modifier,
        colorItemBorderWidth: ColorIndicatorBorderWidth = ColorPickerDefaults.colorIndicatorBorderWidth(),
        state: LazyListState = rememberLazyListState(),
        contentPadding: PaddingValues = PaddingValues(0.dp),
        reverseLayout: Boolean = false,
        horizontalArrangement: Arrangement.Horizontal =
            if (!reverseLayout) Arrangement.Start else Arrangement.End,
        verticalAlignment: Alignment.Vertical = Alignment.Top,
        flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled: Boolean = true
    ) {
        LazyRow(
            modifier = modifier.semantics {
                collectionInfo = CollectionInfo(
                    rowCount = 1,
                    columnCount = colors.size
                )
            },
            state = state,
            contentPadding = contentPadding,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
        ) {
            items(
                items = colors,
                key = { colorItem -> colorItem.color?.toArgb() ?: 0 },
                contentType = { colorItem -> colorItem.color?.toArgb() }
            ) { colorItem ->
                // TODO: Add Modifier.semantics { collectionItemInfo }
                ColorItem(
                    colorItem = colorItem,
                    selected = colorItem.color == selectedColor,
                    onClick = { onColorSelected(colorItem.color) },
                    colorItemBorderWidth = colorItemBorderWidth
                )
            }
        }
    }
}

// TODO: Add sizes
@Composable
internal inline fun ColorItem(
    colorItem: ColorItem,
    selected: Boolean,
    crossinline onClick: () -> Unit,
    colorItemBorderWidth: ColorIndicatorBorderWidth,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            // TODO: Move literal value to const
            .padding(4.dp)
            .clip(CircleShape)
            .requiredSize(40.dp)
            .clickable(
                enabled = colorItem.enabled
            ) {
                onClick.invoke()
            }
    ) {
        when {
            colorItem.color != null -> {
                ColorIndicator(
                    color = colorItem.color,
                    selected = selected,
                    colorItemBorderWidth = colorItemBorderWidth
                )
            }
            else -> {
                ColorNullIndicator(
                    selected = selected
                )
            }
        }
    }
}

@Composable
internal fun ColorIndicator(
    color: Color,
    selected: Boolean,
    colorItemBorderWidth: ColorIndicatorBorderWidth
) {
    val selectedColor = if (color.luminance() < 0.5) {
        Color.White
    } else {
        Color.Black
    }
    val borderWidth = when {
        selected -> colorItemBorderWidth.selectedBorderWidth
        color.luminance() !in 0.1..0.9 -> colorItemBorderWidth.neutralBorderWidth
        else -> colorItemBorderWidth.unselectedBorderWidth
    }

    // Transparent background pattern
    Box(
        modifier = Modifier
            // TODO: Move literal value to const
            .width(20.dp)
            .fillMaxHeight()
            .drawBehind {
                drawRect(TransparentContrastColor)
            }
    )
    // Color indicator
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(color)
            }
            .border(
                width = borderWidth,
                color = selectedColor,
                shape = CircleShape,
            )
    ) {
        if (selected) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_check_24px
                ),
                contentDescription = stringResource(R.string.selected),
                tint = selectedColor,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

// TODO: Remove BoxScope
@Composable
internal fun BoxScope.ColorNullIndicator(
    selected: Boolean
) {
    // TODO: Merge components and update null indicator selection UX.
    if (selected) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .background(Color(0xFFFAFAFA)),
        )
    }
    // Color null indicator
    Icon(
        imageVector = ImageVector.vectorResource(
            R.drawable.ic_format_color_reset_24px
        ),
        contentDescription = stringResource(R.string.color_off),
        modifier = Modifier.align(Alignment.Center),
    )
}

@Immutable
data class ColorIndicatorBorderWidth internal constructor(
    val neutralBorderWidth: Dp,
    val selectedBorderWidth: Dp,
    val unselectedBorderWidth: Dp,
)

object ColorPickerDefaults {
    private val NeutralBorderWidth: Dp = 1.dp

    private val SelectedBorderWidth: Dp = 2.dp

    private val UnselectedBorderWidth: Dp = Dp.Unspecified

    @Stable
    fun colorIndicatorBorderWidth(
        neutralBorderWidth: Dp = NeutralBorderWidth,
        selectedBorderWidth: Dp = SelectedBorderWidth,
        unselectedBorderWidth: Dp = UnselectedBorderWidth,
    ): ColorIndicatorBorderWidth =
        ColorIndicatorBorderWidth(
            neutralBorderWidth = neutralBorderWidth,
            selectedBorderWidth = selectedBorderWidth,
            unselectedBorderWidth = unselectedBorderWidth
        )
}

private val TransparentContrastColor: Color = Color(0xFFBDBDBD)

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

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
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
import dev.sergiobelda.pigment.R

/**
 */
object ColorPicker {

    /**
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerFlowRowSample
     */
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun FlowRow(
        colors: List<ColorPickerItem>,
        selectedColor: Color,
        onColorSelected: (color: Color) -> Unit,
        modifier: Modifier = Modifier,
        shape: Shape = ColorPickerDefaults.Shape,
        size: ColorPickerSize = ColorPickerDefaults.Size,
        colorIndicatorColors: ColorIndicatorColors = ColorPickerDefaults.colorIndicatorColors(),
        colorIndicatorBorderWidth: ColorIndicatorBorderWidth = ColorPickerDefaults.colorIndicatorBorderWidth(),
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
                    columnCount = colors.size.coerceAtMost(maxItemsInEachRow),
                )
            },
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow,
            maxLines = maxLines,
            overflow = overflow,
        ) {
            colors.forEach { colorItem ->
                // TODO: Add Modifier.semantics { collectionItemInfo }
                ColorItem(
                    colorItem = colorItem,
                    selected = colorItem.color == selectedColor,
                    onClick = { onColorSelected(colorItem.color) },
                    shape = shape,
                    size = size,
                    colorIndicatorColors = colorIndicatorColors,
                    colorIndicatorBorderWidth = colorIndicatorBorderWidth,
                )
            }
        }
    }

    /**
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerLazyRowSample
     */
    @Composable
    fun LazyRow(
        colors: List<ColorPickerItem>,
        selectedColor: Color,
        onColorSelected: (color: Color) -> Unit,
        modifier: Modifier = Modifier,
        shape: Shape = ColorPickerDefaults.Shape,
        size: ColorPickerSize = ColorPickerDefaults.Size,
        colorIndicatorColors: ColorIndicatorColors = ColorPickerDefaults.colorIndicatorColors(),
        colorIndicatorBorderWidth: ColorIndicatorBorderWidth = ColorPickerDefaults.colorIndicatorBorderWidth(),
        state: LazyListState = rememberLazyListState(),
        contentPadding: PaddingValues = PaddingValues(0.dp),
        reverseLayout: Boolean = false,
        horizontalArrangement: Arrangement.Horizontal =
            if (!reverseLayout) Arrangement.Start else Arrangement.End,
        verticalAlignment: Alignment.Vertical = Alignment.Top,
        flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled: Boolean = true,
    ) {
        LazyRow(
            modifier = modifier.semantics {
                collectionInfo = CollectionInfo(
                    rowCount = 1,
                    columnCount = colors.size,
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
                key = { colorItem -> colorItem.color.toArgb() },
                contentType = { colorItem -> colorItem.color.toArgb() },
            ) { colorItem ->
                // TODO: Add Modifier.semantics { collectionItemInfo }
                ColorItem(
                    colorItem = colorItem,
                    selected = colorItem.color == selectedColor,
                    onClick = { onColorSelected(colorItem.color) },
                    shape = shape,
                    size = size,
                    colorIndicatorColors = colorIndicatorColors,
                    colorIndicatorBorderWidth = colorIndicatorBorderWidth,
                )
            }
        }
    }
}

@Composable
internal inline fun ColorItem(
    colorItem: ColorPickerItem,
    selected: Boolean,
    crossinline onClick: () -> Unit,
    shape: Shape,
    size: ColorPickerSize,
    colorIndicatorColors: ColorIndicatorColors,
    colorIndicatorBorderWidth: ColorIndicatorBorderWidth,
    modifier: Modifier = Modifier,
) {
    val indicatorSize = size.indicatorSize()
    Box(
        modifier = modifier
            .padding(size.itemSpacing())
            .clip(shape)
            .requiredSize(indicatorSize)
            .clickable(
                enabled = colorItem.enabled,
                indication = ripple(),
                interactionSource = null,
            ) {
                onClick.invoke()
            },
    ) {
        when {
            colorItem.color != Color.Unspecified -> {
                ColorIndicator(
                    color = colorItem.color,
                    selected = selected,
                    shape = shape,
                    indicatorSize = indicatorSize,
                    colorIndicatorColors = colorIndicatorColors,
                    colorIndicatorBorderWidth = colorIndicatorBorderWidth,
                )
            }

            else -> {
                ColorUnspecifiedIndicator(
                    selected = selected,
                )
            }
        }
    }
}

@Composable
internal fun ColorIndicator(
    color: Color,
    selected: Boolean,
    shape: Shape,
    indicatorSize: Dp,
    colorIndicatorColors: ColorIndicatorColors,
    colorIndicatorBorderWidth: ColorIndicatorBorderWidth,
) {
    val selectedColor = colorIndicatorColors.checkColor(color)
    val borderWidth = colorIndicatorBorderWidth.borderWidth(color, selected)
    val translucentColorWidth = remember(indicatorSize) { indicatorSize / 2 }

    // Translucent color background pattern
    Box(
        modifier = Modifier
            .width(translucentColorWidth)
            .fillMaxHeight()
            .drawBehind {
                drawRect(TranslucentContrastColor)
            },
    )
    // Color indicator
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color)
            }
            .border(
                width = borderWidth,
                color = selectedColor,
                shape = shape,
            ),
    ) {
        if (selected) {
            Image(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_check_24px,
                ),
                contentDescription = stringResource(R.string.selected),
                colorFilter = ColorFilter.tint(selectedColor),
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
internal fun ColorUnspecifiedIndicator(
    selected: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(ColorUnspecifiedIndicatorBackgroundColor)
            },
    ) {
        Image(
            imageVector = ImageVector.vectorResource(
                R.drawable.ic_format_color_reset_24px,
            ),
            contentDescription = stringResource(R.string.color_off),
            modifier = Modifier
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(
                color = if (selected) {
                    ColorUnspecifiedIndicatorIconSelectedColor
                } else {
                    ColorUnspecifiedIndicatorIconUnselectedColor
                },
            ),
        )
        if (selected) {
            Image(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_check_24px,
                ),
                contentDescription = stringResource(R.string.selected),
                modifier = Modifier.align(Alignment.Center),
                colorFilter = ColorFilter.tint(ColorUnspecifiedIndicatorSelectedColor),
            )
        }
    }
}

@Stable
enum class ColorPickerSize {
    Small,
    Medium,
    Large,
    ;

    internal fun indicatorSize(): Dp =
        when (this) {
            Small -> ColorPickerSizeSmall
            Medium -> ColorPickerSizeMedium
            Large -> ColorPickerSizeLarge
        }

    internal fun itemSpacing(): Dp =
        when (this) {
            Small,
            Medium,
            Large,
            -> ColorItemSpacing
        }
}

@Immutable
data class ColorIndicatorBorderWidth internal constructor(
    private val neutralBorderWidth: Dp,
    private val selectedBorderWidth: Dp,
    private val unselectedBorderWidth: Dp,
) {
    @Stable
    internal fun borderWidth(color: Color, selected: Boolean): Dp =
        when {
            selected -> selectedBorderWidth
            color.luminance() !in 0.1..0.9 -> neutralBorderWidth
            else -> unselectedBorderWidth
        }
}

@Immutable
data class ColorIndicatorColors internal constructor(
    private val onDarkColor: Color,
    private val onLightColor: Color,
) {
    @Stable
    internal fun checkColor(color: Color): Color =
        if (color.luminance() < 0.5) {
            onDarkColor
        } else {
            onLightColor
        }
}

object ColorPickerDefaults {
    val Shape: Shape = CircleShape

    val Size: ColorPickerSize = ColorPickerSize.Small

    @Stable
    fun colorIndicatorBorderWidth(
        neutralBorderWidth: Dp = NeutralBorderWidth,
        selectedBorderWidth: Dp = SelectedBorderWidth,
        unselectedBorderWidth: Dp = UnselectedBorderWidth,
    ): ColorIndicatorBorderWidth =
        ColorIndicatorBorderWidth(
            neutralBorderWidth = neutralBorderWidth,
            selectedBorderWidth = selectedBorderWidth,
            unselectedBorderWidth = unselectedBorderWidth,
        )

    @Stable
    fun colorIndicatorColors(
        onDarkColor: Color = Color.White,
        onLightColor: Color = Color.Black,
    ): ColorIndicatorColors =
        ColorIndicatorColors(
            onDarkColor = onDarkColor,
            onLightColor = onLightColor,
        )

    private val NeutralBorderWidth: Dp = 1.dp

    private val SelectedBorderWidth: Dp = 2.dp

    private val UnselectedBorderWidth: Dp = Dp.Unspecified
}

private const val ColorUnspecifiedIndicatorIconAlpha: Float = 0.2f

private val ColorUnspecifiedIndicatorIconSelectedColor: Color = Color.Black.copy(
    alpha = ColorUnspecifiedIndicatorIconAlpha,
)

private val ColorUnspecifiedIndicatorIconUnselectedColor: Color = Color.Black

private val ColorUnspecifiedIndicatorSelectedColor: Color = Color.Black

private val ColorUnspecifiedIndicatorBackgroundColor: Color = Color.White

private val ColorItemSpacing: Dp = 6.dp

private val ColorPickerSizeSmall: Dp = 36.dp

private val ColorPickerSizeMedium: Dp = 48.dp

private val ColorPickerSizeLarge: Dp = 56.dp

private val TranslucentContrastColor: Color = Color(0xFFBDBDBD)

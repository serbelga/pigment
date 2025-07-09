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
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.pigment.colorpicker.ColorPicker.FlowRow
import dev.sergiobelda.pigment.resources.Res
import dev.sergiobelda.pigment.resources.color_off
import dev.sergiobelda.pigment.resources.ic_check_24px
import dev.sergiobelda.pigment.resources.ic_format_color_reset_24px
import dev.sergiobelda.pigment.resources.selected
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

/**
 * A tool that allow user select a color item.
 */
object ColorPicker {

    /**
     * A ColorPicker that follows the [FlowRow] layout that receives a list of [ColorPickerItem].
     *
     * @param colors The list of [ColorPickerItem] to be displayed.
     * @param selectedColor The selected [Color].
     * @param onColorSelected Launched when a color item has been selected.
     * @param modifier The modifier to be applied to the Row.
     * @param shape Shape of the color items.
     * @param size The [ColorPickerSize].
     * @param colorIndicatorColors The colors to be applied on the selected indicator for a Color item.
     * @param colorIndicatorBorderWidth The border width to be applied on the selected indicator for a Color item.
     * @param horizontalArrangement The horizontal arrangement of the layout's children.
     * @param verticalArrangement The vertical arrangement of the layout's virtual rows.
     * @param maxItemsInEachRow The maximum number of items per row.
     * @param maxLines The max number of rows.
     *
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerFlowRowSample
     *
     * @see FlowRow
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
    ) {
        ColorPickerFlowRow(
            itemsCount = colors.size,
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow,
            maxLines = maxLines,
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
     * A ColorPicker that follows the [FlowRow] layout that receives a map of a key which
     * value is a [ColorPickerItem]. This component is useful when we need to relate a custom object
     * that needs to be selected with a [ColorPickerItem].
     *
     * @param colorsMap A map of [Any] and its [ColorPickerItem] value.
     * @param selectedItem The selected item.
     * @param onItemSelected Launched when an item has been selected.
     * @param modifier The modifier to be applied to the Row.
     * @param shape Shape of the color items.
     * @param size The [ColorPickerSize].
     * @param colorIndicatorColors The colors to be applied on the selected indicator for a Color item.
     * @param colorIndicatorBorderWidth The border width to be applied on the selected indicator for a Color item.
     * @param horizontalArrangement The horizontal arrangement of the layout's children.
     * @param verticalArrangement The vertical arrangement of the layout's virtual rows.
     * @param maxItemsInEachRow The maximum number of items per row.
     * @param maxLines The max number of rows.
     *
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerFlowRowMapSample
     *
     * @see FlowRow
     */
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun <T : Any> FlowRow(
        colorsMap: Map<T, ColorPickerItem>,
        selectedItem: T,
        onItemSelected: (item: T) -> Unit,
        modifier: Modifier = Modifier,
        shape: Shape = ColorPickerDefaults.Shape,
        size: ColorPickerSize = ColorPickerDefaults.Size,
        colorIndicatorColors: ColorIndicatorColors = ColorPickerDefaults.colorIndicatorColors(),
        colorIndicatorBorderWidth: ColorIndicatorBorderWidth = ColorPickerDefaults.colorIndicatorBorderWidth(),
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        maxItemsInEachRow: Int = Int.MAX_VALUE,
        maxLines: Int = Int.MAX_VALUE,
    ) {
        ColorPickerFlowRow(
            itemsCount = colorsMap.size,
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow,
            maxLines = maxLines,
        ) {
            colorsMap.forEach { pair ->
                // TODO: Add Modifier.semantics { collectionItemInfo }
                ColorItem(
                    colorItem = pair.value,
                    selected = pair.key == selectedItem,
                    onClick = { onItemSelected(pair.key) },
                    shape = shape,
                    size = size,
                    colorIndicatorColors = colorIndicatorColors,
                    colorIndicatorBorderWidth = colorIndicatorBorderWidth,
                )
            }
        }
    }

    /**
     * A ColorPicker that follows the [LazyRow] layout that receives a list of [ColorPickerItem].
     *
     * @param colors The list of [ColorPickerItem] to be displayed.
     * @param selectedColor The selected [Color].
     * @param onColorSelected Launched when a color item has been selected.
     * @param modifier the modifier to apply to this layout
     * @param shape Shape of the color items.
     * @param size The [ColorPickerSize].
     * @param colorIndicatorColors The colors to be applied on the selected indicator for a Color item.
     * @param colorIndicatorBorderWidth The border width to be applied on the selected indicator for a Color item.
     * @param state the state object to be used to control or observe the list's state
     * @param contentPadding a padding around the whole content. This will add padding for the
     * content after it has been clipped, which is not possible via [modifier] param. You can use it
     * to add a padding before the first item or after the last one. If you want to add a spacing
     * between each item use [horizontalArrangement].
     * @param reverseLayout reverse the direction of scrolling and layout. When `true`, items are
     * laid out in the reverse order and [LazyListState.firstVisibleItemIndex] == 0 means
     * that row is scrolled to the end. Note that [reverseLayout] does not change the behavior of
     * [horizontalArrangement], e.g. with [Arrangement.Start] [123###] becomes [321###].
     * @param horizontalArrangement The horizontal arrangement of the layout's children. This allows
     * to add a spacing between items and specify the arrangement of the items when we have not enough
     * of them to fill the whole minimum size.
     * @param verticalAlignment the vertical alignment applied to the items
     * @param flingBehavior logic describing fling behavior.
     * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
     * is allowed. You can still scroll programmatically using the state even when it is disabled.
     *
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerLazyRowSample
     *
     * @see LazyRow
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
        ColorPickerLazyRow(
            itemsCount = colors.size,
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
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

    /**
     * A ColorPicker that follows the [LazyRow] layout that receives a map of a key which
     * value is a [ColorPickerItem]. This component is useful when we need to relate a custom object
     * that needs to be selected with a [ColorPickerItem].
     *
     * @param colorsMap A map of [Any] and its [ColorPickerItem] value.
     * @param selectedItem The selected item.
     * @param onItemSelected Launched when an item has been selected.
     * @param modifier the modifier to apply to this layout
     * @param shape Shape of the color items.
     * @param size The [ColorPickerSize].
     * @param colorIndicatorColors The colors to be applied on the selected indicator for a Color item.
     * @param colorIndicatorBorderWidth The border width to be applied on the selected indicator for a Color item.
     * @param state the state object to be used to control or observe the list's state
     * @param contentPadding a padding around the whole content. This will add padding for the
     * content after it has been clipped, which is not possible via [modifier] param. You can use it
     * to add a padding before the first item or after the last one. If you want to add a spacing
     * between each item use [horizontalArrangement].
     * @param reverseLayout reverse the direction of scrolling and layout. When `true`, items are
     * laid out in the reverse order and [LazyListState.firstVisibleItemIndex] == 0 means
     * that row is scrolled to the end. Note that [reverseLayout] does not change the behavior of
     * [horizontalArrangement], e.g. with [Arrangement.Start] [123###] becomes [321###].
     * @param horizontalArrangement The horizontal arrangement of the layout's children. This allows
     * to add a spacing between items and specify the arrangement of the items when we have not enough
     * of them to fill the whole minimum size.
     * @param verticalAlignment the vertical alignment applied to the items
     * @param flingBehavior logic describing fling behavior.
     * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
     * is allowed. You can still scroll programmatically using the state even when it is disabled.
     *
     * @sample dev.sergiobelda.pigment.samples.colorpicker.ColorPickerLazyRowMapSample
     *
     * @see LazyRow
     */
    @Composable
    fun <T : Any> LazyRow(
        colorsMap: Map<T, ColorPickerItem>,
        selectedItem: T,
        onItemSelected: (item: T) -> Unit,
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
        ColorPickerLazyRow(
            itemsCount = colorsMap.size,
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
        ) {
            colorsMap.forEach { pair ->
                item(
                    key = pair.value.color.toArgb(),
                    contentType = pair.value.color.toArgb(),
                ) {
                    // TODO: Add Modifier.semantics { collectionItemInfo }
                    ColorItem(
                        colorItem = pair.value,
                        selected = pair.key == selectedItem,
                        onClick = { onItemSelected(pair.key) },
                        shape = shape,
                        size = size,
                        colorIndicatorColors = colorIndicatorColors,
                        colorIndicatorBorderWidth = colorIndicatorBorderWidth,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorPickerFlowRow(
    itemsCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    maxItemsInEachRow: Int,
    maxLines: Int,
    content: @Composable FlowRowScope.() -> Unit,
) = FlowRow(
    modifier = modifier.semantics {
        collectionInfo = CollectionInfo(
            rowCount = maxLines,
            columnCount = itemsCount.coerceAtMost(maxItemsInEachRow),
        )
    },
    horizontalArrangement = horizontalArrangement,
    verticalArrangement = verticalArrangement,
    maxItemsInEachRow = maxItemsInEachRow,
    maxLines = maxLines,
    content = content,
)

@Composable
private fun ColorPickerLazyRow(
    itemsCount: Int,
    modifier: Modifier,
    state: LazyListState,
    contentPadding: PaddingValues,
    reverseLayout: Boolean,
    horizontalArrangement: Arrangement.Horizontal,
    verticalAlignment: Alignment.Vertical,
    flingBehavior: FlingBehavior,
    userScrollEnabled: Boolean,
    content: LazyListScope.() -> Unit,
) = LazyRow(
    modifier = modifier.semantics {
        collectionInfo = CollectionInfo(
            rowCount = 1,
            columnCount = itemsCount,
        )
    },
    state = state,
    contentPadding = contentPadding,
    reverseLayout = reverseLayout,
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment,
    flingBehavior = flingBehavior,
    userScrollEnabled = userScrollEnabled,
    content = content,
)

@Composable
internal inline fun ColorItem(
    colorItem: ColorPickerItem,
    selected: Boolean,
    crossinline onClick: () -> Unit,
    shape: Shape,
    size: ColorPickerSize,
    colorIndicatorColors: ColorIndicatorColors,
    colorIndicatorBorderWidth: ColorIndicatorBorderWidth,
) {
    Box(
        modifier = Modifier
            .padding(size.itemSpacing())
            .graphicsLayer {
                this.shape = shape
                clip = true
            }
            .clickable(
                enabled = colorItem.enabled,
                indication = ripple(),
                interactionSource = null,
            ) {
                onClick.invoke()
            },
    ) {
        val indicatorSize = size.indicatorSize()
        Box(
            modifier = Modifier
                .padding(ColorItemInnerPadding)
                .graphicsLayer {
                    this.shape = shape
                    clip = true
                }
                .requiredSize(indicatorSize),
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
                imageVector = vectorResource(
                    Res.drawable.ic_check_24px,
                ),
                contentDescription = stringResource(Res.string.selected),
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
            imageVector = vectorResource(
                Res.drawable.ic_format_color_reset_24px,
            ),
            contentDescription = stringResource(Res.string.color_off),
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
                imageVector = vectorResource(Res.drawable.ic_check_24px),
                contentDescription = stringResource(Res.string.selected),
                modifier = Modifier.align(Alignment.Center),
                colorFilter = ColorFilter.tint(ColorUnspecifiedIndicatorSelectedColor),
            )
        }
    }
}

/**
 * Represents the size of items in Color Picker.
 */
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

/**
 * Color indicator border width on different states.
 */
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

/**
 * Colors used on the UI elements of the color indicator that is on a specific state
 * (e.g. selected, ...).
 */
@Immutable
@ConsistentCopyVisibility
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

/**
 * Defaults used in [ColorPicker].
 */
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

private val ColorItemSpacing: Dp = 4.dp

private val ColorItemInnerPadding: Dp = 2.dp

private val ColorPickerSizeSmall: Dp = 36.dp

private val ColorPickerSizeMedium: Dp = 48.dp

private val ColorPickerSizeLarge: Dp = 56.dp

private val TranslucentContrastColor: Color = Color(0xFFBDBDBD)

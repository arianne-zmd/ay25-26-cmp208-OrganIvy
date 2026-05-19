package com.example.organivy.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.organivy.R
import kotlinx.coroutines.delay

@Composable
fun SpriteLayer(
    drawableId: Int,
    row: Int = 0,
    column: Int = 0,
    columnsCount: Int = 8,
    rowsCount: Int = 8,
    modifier: Modifier = Modifier
) {
    val bitmap = ImageBitmap.imageResource(drawableId)

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val spriteWidth = bitmap.width / columnsCount
        val spriteHeight = bitmap.height / rowsCount

        drawImage(
            image = bitmap,
            srcOffset = IntOffset(column * spriteWidth, row * spriteHeight),
            srcSize = IntSize(spriteWidth, spriteHeight),
            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
            filterQuality = FilterQuality.None
        )
    }
}

/**
 * Stacks layers and performs a smooth "Pixel Bounce" idle animation.
 */
@Composable
fun LayeredCharacter(
    baseId: Int,
    hairId: Int? = null,
    outfitId: Int? = null,
    row: Int = 0,
    column: Int = 0,
    columnsCount: Int = 1,
    rowsCount: Int = 1,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.offset(y = 4.dp)) {
        SpriteLayer(drawableId = baseId, row = row, column = column, columnsCount = columnsCount, rowsCount = rowsCount)
        outfitId?.let { SpriteLayer(drawableId = it, row = row, column = column, columnsCount = columnsCount, rowsCount = rowsCount) }
        hairId?.let { SpriteLayer(drawableId = it, row = row, column = column, columnsCount = columnsCount, rowsCount = rowsCount) }
    }
}

@Composable
fun JumpingCharacter(
    baseId: Int,
    hairId: Int? = null,
    outfitId: Int? = null,
    modifier: Modifier = Modifier
) {
    var isJumping by remember { mutableStateOf(false) }
    var currentFrame by remember { mutableStateOf(0) }
    
    // Determine if we should treat this as a sheet or a single image
    // If it's a single image, we don't slice it into columns
    val isSheet = baseId == R.drawable.character_base_sheet || baseId.toString().contains("sheet", ignoreCase = true)
    val totalColumns = if (isSheet) 8 else 1

    LaunchedEffect(isJumping) {
        if (isJumping && isSheet) {
            for (i in 0 until totalColumns) {
                currentFrame = i
                delay(80)
            }
            currentFrame = 0
            isJumping = false
        } else if (isJumping) {
            // If it's a single image, we can "jump" the whole box instead of cycling frames
            delay(300) 
            isJumping = false
        }
    }

    // Animation for the physical "jump" (moving the box up and down)
    val jumpOffset by animateDpAsState(
        targetValue = if (isJumping) (-20).dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "JumpHeight"
    )

    Box(
        modifier = modifier
            .offset { IntOffset(0, jumpOffset.roundToPx()) }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (!isJumping) isJumping = true
            }
    ) {
        LayeredCharacter(
            baseId = baseId,
            hairId = hairId,
            outfitId = outfitId,
            column = if (isSheet) currentFrame else 0,
            columnsCount = totalColumns,
            rowsCount = 1
        )
    }
}

@Composable
fun SpriteCharacter(
    row: Int = 0,
    column: Int = 0,
    modifier: Modifier = Modifier
) {
    SpriteLayer(
        drawableId = R.drawable.character_base_single_green, 
        row = row, 
        column = column, 
        columnsCount = 1, 
        rowsCount = 1, 
        modifier = modifier
    )
}

@Composable
fun AnimatedSpriteCharacter(
    row: Int = 0,
    frameCount: Int = 4,
    modifier: Modifier = Modifier
) {
    LayeredCharacter(
        baseId = R.drawable.character_base_single_green,
        row = row,
        columnsCount = 1,
        rowsCount = 1,
        modifier = modifier
    )
}

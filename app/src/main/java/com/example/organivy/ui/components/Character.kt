package com.example.organivy.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.organivy.R

@Composable
fun SpriteLayer(
    drawableId: Int,
    row: Int = 0,
    column: Int = 0,
    modifier: Modifier = Modifier
) {
    val bitmap = ImageBitmap.imageResource(drawableId)
    val gridSize = 8

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val spriteWidth = bitmap.width / gridSize
        val spriteHeight = bitmap.height / gridSize

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
    isIdle: Boolean = true,
    modifier: Modifier = Modifier
) {
    // Smooth vertical bounce for idle state
    val transition = rememberInfiniteTransition(label = "IdleBounce")
    val bounceOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (isIdle) 4f else 0f, // 4dp bounce
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bounce"
    )

    Box(modifier = modifier.offset(y = bounceOffset.dp)) {
        // We use column 0 because Row 0 Frame 0 is the standing pose
        SpriteLayer(drawableId = baseId, row = row, column = 0)
        outfitId?.let { SpriteLayer(drawableId = it, row = row, column = 0) }
        hairId?.let { SpriteLayer(drawableId = it, row = row, column = 0) }
    }
}

@Composable
fun SpriteCharacter(
    row: Int = 0,
    column: Int = 0,
    modifier: Modifier = Modifier
) {
    SpriteLayer(drawableId = R.drawable.char_base_shade1, row = row, column = column, modifier = modifier)
}

@Composable
fun AnimatedSpriteCharacter(
    row: Int = 0,
    frameCount: Int = 4,
    modifier: Modifier = Modifier
) {
    LayeredCharacter(
        baseId = R.drawable.char_base_shade1,
        row = row,
        modifier = modifier
    )
}

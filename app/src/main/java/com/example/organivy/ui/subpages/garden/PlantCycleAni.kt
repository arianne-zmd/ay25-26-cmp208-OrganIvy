package com.example.organivy.ui.subpages.garden

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import com.example.organivy.R

/**
 * Configuration for a single sprite sheet animation stage.
 */
data class SpriteConfig(
    val image: ImageBitmap,
    val frameCount: Int,
    val frameDurations: List<Long>
)

@Composable
fun BaseSpriteAnimation(
    config: SpriteConfig,
    onAnimationFinished: () -> Unit = {}
) {
    var currentFrame by remember { mutableStateOf(0) }

    LaunchedEffect(config) {
        for (f in 0 until config.frameCount) {
            currentFrame = f
            delay(config.frameDurations.getOrElse(f) { 200L })
        }
        onAnimationFinished()
    }

    val frameWidth = config.image.width / config.frameCount
    val frameHeight = config.image.height

    Canvas(
        modifier = Modifier.size(200.dp)
    ) {
        drawImage(
            image = config.image,
            srcOffset = IntOffset(
                x = currentFrame * frameWidth,
                y = 0
            ),
            srcSize = IntSize(
                frameWidth,
                frameHeight
            )
        )
    }
}

@Composable
fun SeedsFallingAnimation(onAnimationFinished: () -> Unit = {}) {
    val config = SpriteConfig(
        image = ImageBitmap.imageResource(R.drawable.seeds_falling),
        frameCount = 3,
        frameDurations = listOf(400L, 400L, 400L)
    )
    BaseSpriteAnimation(config, onAnimationFinished)
}

@Composable
fun PlantBuddingAnimation(onAnimationFinished: () -> Unit = {}) {
    val config = SpriteConfig(
        image = ImageBitmap.imageResource(R.drawable.plant_budding),
        frameCount = 3,
        frameDurations = listOf(300L, 600L, 1000L)
    )
    BaseSpriteAnimation(config, onAnimationFinished)
}

@Composable
fun DaisyBloomingAnimation(onAnimationFinished: () -> Unit = {}) {
    val config = SpriteConfig(
        image = ImageBitmap.imageResource(R.drawable.daisy_blooming),
        frameCount = 3,
        frameDurations = listOf(400L, 400L, 400L)
    )
    BaseSpriteAnimation(config, onAnimationFinished)
}

@Composable
fun FinalShotAnimation(onAnimationFinished: () -> Unit = {}) {
    val config = SpriteConfig(
        image = ImageBitmap.imageResource(R.drawable.daisy_blooming_final),
        frameCount = 3,
        frameDurations = listOf(500L, 500L, 2000L)
    )
    BaseSpriteAnimation(config, onAnimationFinished)
}

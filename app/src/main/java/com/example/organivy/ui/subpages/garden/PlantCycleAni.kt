package com.example.organivy.ui.subpages.garden

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import com.example.organivy.R



@Composable
fun SpriteAnimation() {


    val spriteSheet = ImageBitmap.imageResource(id = R.drawable.plant_budding)

    val frameCount = 3
    val frameWidth = spriteSheet.width / frameCount
    val frameHeight = spriteSheet.height

    val frameDurations = listOf(
        100L,
        400L,
        1000L
    )

    var currentFrame by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {

        /* this is for it to continue foreverrrrrrr
        while (true) {

            delay(100)

            currentFrame =
                (currentFrame + 1) % frameCount
        }*/

        while (currentFrame < frameCount - 1) {

            delay(frameDurations[currentFrame])

            currentFrame++
        }
    }

    Canvas(
        modifier = Modifier.size(200.dp)
    ) {

        drawImage(
            image = spriteSheet,

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

@Preview(showBackground = true)
@Preview(device = Devices.PIXEL_7)
@Preview(widthDp = 300, heightDp = 400)
@Composable
fun PreviewSpriteAnimation() {
    SpriteAnimation(

    )
}

package com.example.organivy.ui.subpages.garden

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.R
import com.example.organivy.data.Header
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun WorldMapSubscreen(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val photoViewModel = viewModel<PhotoViewModel>()
        val gameViewModel = viewModel<GameViewModel>()
        Header(photoViewModel = photoViewModel, gameViewModel = gameViewModel)

        Text(
            text = "Map",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        GameCanvas()
    }
}

@Composable
fun GameCanvas(modifier: Modifier = Modifier) {
    var cameraX by remember { mutableStateOf(0f) }
    var cameraY by remember { mutableStateOf(0f) }

    // Map data (Your grid logic)
    val mapData = remember {
        listOf(
            List(100) { 1 },
            listOf(1, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1) + List(88) { 1 },
            listOf(2, 2, 3, 3, 1, 1, 4, 4, 3, 1, 2, 4) + List(88) { 1 },
            listOf(3, 3, 1, 1, 2, 2, 3, 5, 5, 5, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 5, 5, 1, 3, 5, 3, 5, 5, 3, 1, 1, 3, 1, 1, 1, 5, 5, 1, 4, 3, 3, 3, 3, 3, 4, 3, 1) + List(43) { 1 },
            listOf(1, 2, 3, 1, 5, 5, 5, 5, 5, 5, 5, 4, 5, 5, 5, 5, 1, 1, 3, 5, 1, 3, 1, 1, 5, 5, 1, 1, 1, 5, 5, 5, 5, 3, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 2, 3, 2, 3, 3, 3, 3, 3, 1, 3, 1, 1) + List(42) { 1 },
            listOf(2, 2, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 1, 5, 5, 5, 1, 1, 5, 1, 5, 5, 5, 5, 5, 1, 1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 3) + List(41) { 1 },
            listOf(3, 3, 1, 5) + List(7) { 5 } + List(3) { 9 } + List(35) { 5 } + List(5) { 3 } + List(10) { 5 } + List(36) { 1 },
            listOf(1, 2, 3) + List(7) { 5 } + List(3) { 9 } + List(36) { 5 } + List(5) { 3 } + List(20) { 5 } + List(26) { 1 },
            listOf(1, 2, 3, 1) + List(7) { 5 } + List(3) { 9 } + List(35) { 5 } + List(5) { 3 } + List(20) { 5 } + List(26) { 1 },
            listOf(1, 2, 3, 1, 2) + List(44) { 5 } + List(5) { 3 } + List(24) { 5 } + List(22) { 1 },
            listOf(1, 2, 3, 2, 3) + List(44) { 5 } + List(5) { 3 } + List(25) { 5 } + List(21) { 1 },
            listOf(1, 2, 3, 1, 2) + List(44) { 5 } + List(5) { 3 } + List(26) { 5 } + List(20) { 1 },
            listOf(1, 2, 3) + List(46) { 5 } + List(5) { 3 } + List(25) { 5 } + List(21) { 1 },
            listOf(1, 2) + List(47) { 5 } + List(5) { 3 } + List(24) { 5 } + List(22) { 1 },
            listOf(1, 2, 5) + List(46) { 5 } + List(5) { 3 } + List(26) { 5 } + List(20) { 1 },
            listOf(2, 2, 3, 5) + List(45) { 5 } + List(5) { 3 } + List(28) { 5 } + List(18) { 1 },
            listOf(3, 3, 1, 1, 5) + List(44) { 5 } + List(5) { 3 } + List(30) { 5 } + List(16) { 1 },
            listOf(2, 2, 3, 3, 1, 5) + List(43) { 5 } + List(5) { 3 } + List(32) { 5 } + List(14) { 1 },
            listOf(3, 3, 1, 1, 2, 2, 5, 5) + List(41) { 5 } + List(5) { 3 } + List(35) { 5 } + List(11) { 1 },
            listOf(1, 2, 3, 1, 2, 5, 5) + List(42) { 5 } + List(5) { 3 } + List(35) { 5 } + List(11) { 1 },
            listOf(2, 2, 3, 2, 5) + List(44) { 5 } + List(5) { 3 } + List(34) { 5 } + List(12) { 1 },
            listOf(2, 2, 3, 5) + List(45) { 5 } + List(5) { 3 } + List(34) { 5 } + List(12) { 1 },
            listOf(3, 3, 1, 1, 5) + List(44) { 5 } + List(5) { 3 } + List(36) { 5 } + List(10) { 1 },
            listOf(2, 2, 3, 5) + List(45) { 5 } + List(5) { 3 } + List(36) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 3, 2),
            listOf(3, 3, 1, 5) + List(45) { 5 } + List(5) { 3 } + List(37) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 1),
            listOf(1, 2, 3, 1, 5) + List(44) { 5 } + List(5) { 3 } + List(39) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1),
            listOf(2, 2, 3, 2, 5) + List(44) { 5 } + List(5) { 3 } + List(40) { 5 } + listOf(2, 2, 3, 1, 2, 4),
            listOf(2, 2, 3, 5) + List(45) { 5 } + List(5) { 3 } + List(40) { 5 } + listOf(2, 2, 3, 1, 2, 4),
            listOf(3, 3, 1, 1, 5) + List(44) { 5 } + List(5) { 3 } + List(42) { 5 } + listOf(2, 2, 3, 1),
            listOf(2, 2, 3, 3, 1, 5) + List(43) { 5 } + List(46) { 3 } + listOf(2, 2, 3, 1, 2),
            listOf(3, 3, 1, 1, 2, 2, 5) + List(42) { 5 } + List(48) { 3 } + listOf(2, 2, 3),
            listOf(1, 2, 3, 1, 2, 4, 5) + List(42) { 5 } + List(48) { 3 } + listOf(2, 2, 3),
            listOf(2, 2, 3, 2, 5, 5) + List(89) { 5 } + listOf(2, 2, 3, 1, 2),
            listOf(2, 2, 3, 5) + List(88) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1),
            listOf(3, 3, 1, 1, 5) + List(87) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1),
            listOf(2, 2, 3, 3, 1, 5) + List(87) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1),
            listOf(3, 3, 1, 1, 2, 2, 5) + List(87) { 5 } + listOf(2, 2, 3, 1, 2, 4),
            listOf(1, 2, 3, 1, 2, 5, 5) + List(20) { 5 } + listOf(2, 2, 3, 1, 2, 4) + List(61) { 5 } + listOf(2, 2, 3, 1, 2, 4),
            listOf(2, 2, 3, 2, 5) + List(20) { 5 } + listOf(2, 2, 3, 1, 2, 4) + List(60) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 2),
            listOf(2, 2, 3, 5) + List(20) { 5 } + listOf(2, 2, 3, 1, 2, 4) + List(60) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 2, 3),
            listOf(3, 3, 1, 1, 5) + List(20) { 5 } + listOf(2, 2, 3, 1, 2, 4) + List(60) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 4),
            listOf(2, 2, 3, 3, 1, 5) + List(86) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1),
            listOf(3, 3, 1, 1, 5) + List(86) { 5 } + listOf(2, 2, 3, 1, 2, 4, 1, 1, 2),
            listOf(1, 2, 3, 1, 2, 5, 5) + List(87) { 5 } + listOf(2, 2, 3, 1, 2, 4),
            listOf(2, 2, 3, 2, 2, 1, 4, 5) + List(80) { 5 } + listOf(5, 4, 2, 2, 3, 1, 2, 2, 4, 1, 1, 4),
            listOf(3, 3, 1, 1, 4, 2, 2, 1, 5) + List(80) { 5 } + listOf(5, 5, 2, 2, 2, 3, 1, 2, 4, 3, 1),
            listOf(1, 2, 3, 1, 2, 3, 1, 1, 2, 5, 5) + List(80) { 5 } + listOf(5, 2, 3, 1, 4, 4, 3, 1, 2),
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 5) + List(80) { 5 } + listOf(5, 2, 3, 1, 4, 4, 1, 2),
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 4, 5) + List(80) { 5 } + listOf(5, 1, 2, 2, 1, 4, 3),
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 3, 2, 5, 5) + List(78) { 5 } + listOf(5, 5, 2, 3, 4, 3, 2),
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 3, 2, 2, 3, 5) + List(11) { 5 } + List(66) { 6 } + listOf(5, 5, 4, 4, 3, 1, 2),
            listOf(3, 3, 1, 1, 4, 2, 2, 1, 4, 2, 1, 1, 2, 3, 4, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 4) + List(71) { 1 },
            listOf(1, 2, 3, 1, 2, 3, 1, 1, 2, 1, 2, 1, 2, 3, 4, 1, 4, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 1, 2, 3, 4, 1, 2, 5, 5, 10, 5, 5, 10, 5, 5, 5, 2, 2) + List(72) { 1 },
            listOf(3, 3, 1, 1, 4, 2, 2, 1, 4, 2, 1, 1, 2, 3, 4, 1, 1, 2, 5, 5, 10, 5, 5, 5, 5, 5, 1, 2, 2) + List(71) { 1 },
            listOf(1, 2, 3, 1, 2, 3, 1, 1, 2, 1, 2, 1, 2, 3, 4, 1, 4, 2, 2, 5, 5, 5, 5, 5, 5, 3, 1, 2, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 1, 2, 3, 4, 1, 2, 2, 2, 3, 5, 5, 5, 5, 4, 3, 1, 2, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 1, 2, 3, 4, 1, 2, 2, 2, 3, 2, 5, 5, 4, 4, 3, 1, 2, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 1, 2, 3, 4, 1, 2, 2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 2, 2, 1, 2, 3, 4, 1, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 2) + List(71) { 1 },
            listOf(2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 2, 1, 2, 3, 4, 1, 2, 2, 3, 2, 3, 1, 4, 4, 3, 1, 2, 2) + List(71) { 1 },
            List(100) { 1 }
        )
    }

    val tileSize = 73f

    // Load Bitmaps
    val water = ImageBitmap.imageResource(R.drawable.basicwater)
    val water1 = ImageBitmap.imageResource(R.drawable.basicwater1)
    val water3 = ImageBitmap.imageResource(R.drawable.basicwater2)
    val water4 = ImageBitmap.imageResource(R.drawable.basicwater3)
    val bottom_edge = ImageBitmap.imageResource(R.drawable.bottom_edge)
    val top_edge = ImageBitmap.imageResource(R.drawable.top_edge)
    val left_edge = ImageBitmap.imageResource(R.drawable.left_edge)
    val grayrock = ImageBitmap.imageResource(R.drawable.grayrock)
    val graystone = ImageBitmap.imageResource(R.drawable.graystone)
    val deflaut_green = ImageBitmap.imageResource(R.drawable.deflaut_green)
    val brown_path = ImageBitmap.imageResource(R.drawable.brown_path)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    cameraX -= dragAmount.x
                    cameraY -= dragAmount.y
                }
            }
    ) {
        // Calculate the range of visible tiles
        val startCol = (cameraX / tileSize).toInt().coerceIn(0, 99)
        val endCol = ((cameraX + size.width) / tileSize).toInt().coerceIn(0, 99)
        val startRow = (cameraY / tileSize).toInt().coerceIn(0, mapData.size - 1)
        val endRow = ((cameraY + size.height) / tileSize).toInt().coerceIn(0, mapData.size - 1)

        // Only loop over visible tiles
        for (row in startRow..endRow) {
            val rowData = mapData[row]
            for (col in startCol..endCol) {
                if (col < rowData.size) {
                    val tileType = rowData[col]
                    val image = when (tileType) {
                        1 -> water
                        2 -> water1
                        3 -> water3
                        4 -> water4
                        5 -> deflaut_green
                        6 -> bottom_edge
                        7 -> top_edge
                        8 -> left_edge
                        9 -> grayrock
                        10 -> graystone
                        11 -> brown_path
                        else -> water
                    }

                    val x = col * tileSize - cameraX
                    val y = row * tileSize - cameraY

                    drawImage(
                        image = image,
                        topLeft = Offset(x, y)
                    )
                }
            }
        }
    }
}

package com.example.organivy.ui.subpages.garden

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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


        Text(
            text = "Map",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )


        Box {
            var playerRow by remember { mutableStateOf(10) }
            var playerCol by remember { mutableStateOf(10) }



            GameCanvas(
                playerRow = playerRow,
                playerCol = playerCol
            )

            MovementControls(
                onUp = { playerRow-- },
                onDown = { playerRow++ },
                onLeft = { playerCol-- },
                onRight = { playerCol++ }
            )

        }
    }

}

@Composable
fun MovementControls(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onUp) { Text("↑") }

        Row {
            Button(onClick = onLeft) { Text("←") }

            Spacer(Modifier.width(20.dp))

            Button(onClick = onRight) { Text("→") }
        }

        Button(onClick = onDown) { Text("↓") }
    }
}


@Composable
fun GameCanvas(
    playerRow: Int,
    playerCol: Int,
    modifier: Modifier = Modifier
){
    //var cameraX by remember { mutableStateOf(0f) }
    //var cameraY by remember { mutableStateOf(0f) }


    var cameraX by remember { mutableStateOf(0f) }
    var cameraY by remember { mutableStateOf(0f) }

    val tileSize = 73f
    val context = LocalContext.current

    val player = ImageBitmap.imageResource(R.drawable.character_base_single_blue)


    val mapData = listOf(

        listOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),
        listOf(1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1),
        listOf(1,1,1,1,2,2,2,5,5,5,5,5,5,5,5,5,2,2,2,1,1,1,1,1,1),
        listOf(1,1,1,2,2,5,9,5,5,5,11,11,11,5,5,5,5,5,2,2,1,1,1,1,1),
        listOf(1,1,2,2,5,5,5,5,5,11,11,11,11,11,5,5,5,5,5,2,2,1,1,1,1),
        listOf(1,1,2,5,5,5,5,5,11,11,5,5,5,11,11,5,5,5,5,5,2,1,1,1,1),
        listOf(1,2,2,5,5,5,9,5,11,5,5,5,5,5,5,5,9,5,5,5,2,2,1,1,1),
        listOf(1,2,5,5,5,5,5,5,11,5,5,10,5,5,5,5,5,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,5,5,5,11,11,5,5,5,5,5,5,5,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,9,5,5,5,11,11,5,5,5,5,5,5,9,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,5,5,5,5,11,10,10,10,5,5,5,5,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,5,5,11,11,11,11,5,5,5,5,5,5,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,5,11,11,5,5,5,5,5,5,11,11,5,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,11,11,5,5,5,9,5,9,5,5,11,11,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,11,11,5,5,5,5,5,5,5,5,5,5,11,11,5,9,5,2,1,1,1),
        listOf(1,2,5,5,5,11,11,5,5,10,5,5,5,10,5,11,11,5,5,5,5,2,1,1,1),
        listOf(1,2,5,5,5,5,11,11,5,5,5,5,5,5,11,11,5,5,5,5,5,2,1,1,1),
        listOf(1,2,2,5,5,5,5,11,11,11,11,11,11,11,11,5,5,5,5,5,2,2,1,1,1),
        listOf(1,1,2,2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2,2,1,1,1,1),
        listOf(1,1,1,2,2,5,5,5,5,5,5,5,9,5,5,5,5,5,2,2,1,1,1,1,1),
        listOf(1,1,1,1,2,2,2,5,5,5,5,5,5,5,5,5,2,2,2,1,1,1,1,1,1),
        listOf(1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1),
        listOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

    )


    val water = remember {
        ImageBitmap.imageResource(
            context.resources,
            R.drawable.basicwater
        )
    }
    val water1 = remember {ImageBitmap.imageResource(context.resources,R.drawable.basicwater1)}
    val water3 = remember {ImageBitmap.imageResource(context.resources,R.drawable.basicwater2)}
    val water4 = remember {ImageBitmap.imageResource(context.resources,R.drawable.basicwater3)}
    val bottom_edge = remember {ImageBitmap.imageResource(context.resources,R.drawable.bottom_edge)}
    val top_edge = remember {ImageBitmap.imageResource(context.resources,R.drawable.top_edge)}
    val left_edge = remember {ImageBitmap.imageResource(context.resources,R.drawable.left_edge)}
    val grayrock = remember {ImageBitmap.imageResource(context.resources,R.drawable.grayrock)}
    val graystone = remember {ImageBitmap.imageResource(context.resources,R.drawable.graystone)}
    val deflaut_green = remember {ImageBitmap.imageResource(context.resources,R.drawable.deflaut_green)}
    val brown_path = remember {ImageBitmap.imageResource(context.resources,R.drawable.brown_path)}

    /*val water = ImageBitmap.imageResource(id = R.drawable.basicwater)
    val water1 = ImageBitmap.imageResource(id=R.drawable.basicwater1)
    val water3 = ImageBitmap.imageResource(id=R.drawable.basicwater2)
    val water4 = ImageBitmap.imageResource(id=R.drawable.basicwater3)
    val bottom_edge = ImageBitmap.imageResource(id=R.drawable.bottom_edge)
    val top_edge = ImageBitmap.imageResource(id=R.drawable.top_edge)
    val left_edge = ImageBitmap.imageResource(id=R.drawable.left_edge)
    val grayrock = ImageBitmap.imageResource(id=R.drawable.grayrock)
    val graystone = ImageBitmap.imageResource(id=R.drawable.graystone)
    val deflaut_green = ImageBitmap.imageResource(id=R.drawable.deflaut_green)
    val brown_path = ImageBitmap.imageResource(id=R.drawable.brown_path)*/

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
    ){
        // camera follows player
        cameraX = playerCol * tileSize - size.width / 2 + tileSize / 2
        cameraY = playerRow * tileSize - size.height / 2 + tileSize / 2

        val playerX = playerCol * tileSize - cameraX
        val playerY = playerRow * tileSize - cameraY

        val startCol = (cameraX / tileSize).toInt().coerceAtLeast(0)
        val startRow = (cameraY / tileSize).toInt().coerceAtLeast(0)

        val endCol = ((cameraX + size.width) / tileSize).toInt().coerceAtMost(mapData[0].size - 1)
        val endRow = ((cameraY + size.height) / tileSize).toInt().coerceAtMost(mapData.size - 1)

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
                        dstOffset = IntOffset(x.toInt(), y.toInt()),
                        dstSize = IntSize(tileSize.toInt(), tileSize.toInt())
                    )
                }
            }
        }



        drawImage(
            image = player,
            dstOffset = IntOffset(playerX.toInt(), playerY.toInt()),
            dstSize = IntSize(tileSize.toInt(), tileSize.toInt())
        )

    }
}

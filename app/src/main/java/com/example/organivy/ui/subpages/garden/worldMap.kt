package com.example.organivy.ui.subpages.garden

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.organivy.R
import com.example.organivy.ui.pages.header





@Composable
fun WorldMapSubscreen(onNavigateToProfile: () -> Unit,
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp,),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        //verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        header()

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

    // Map data
    val mapData = listOf(
        List(100) {1},
        listOf(1,1,1,1,1,1,3,1,1,3,1,1),
        listOf(2,2,3,3,1,1,4,4,3,1,2,4),
        listOf(3,3,1,1,2,2,3,5,5,5,1,1,1,1,5,1,1,1,1,1,1,1,1,3,1,1,3,1,1,1,5,5,1,3,5,3,5,5,3,1,1,3,1,1,1,5,5,1,3,5,3,3,4,5,3,3,1),
        listOf(1,2,3,1,5,5,5,5,5,5,5,4,5,5,1,5,1,1,3,5,1,3,1,1,5,5,1,1,1,5,5,5,5,3,5,5,5,1,1,1,1,1,1,5,5,5,2,3,2,5,5,1,5,1,1,3,1,1),
        listOf(2,2,3,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,5,5,5,5,1,5,5,5,5,5,1,1,5,5,5,1,1,5,1,5,5,5,5,5,1,1,5,5,5,1,1,1,3,1,1,3,1,1),
        listOf(3,3,1,5)  + List(60){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3) + List(61){5} + listOf(2,3,1),

        listOf(1,2,3,1) + List(62){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2) + List(63){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2,3,2,3) + List(64){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2) + List(65){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2,3) + List(66){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2) + List(66){5} + listOf(2,3,1,2,2,3,1,2,4,1,1),
        listOf(1,2,5)  + List(67){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,2,2,5,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2,5,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,2,5,) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5,) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,2,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        //part you dont see
        listOf(2,2,3,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,2,2,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2,4,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,2,5,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,2,2,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2,5,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,2,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),

        listOf(2,2,3,3,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(3,3,1,1,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(1,2,3,1,2,5,5) + List(94){5} + listOf(2,2,3,1,2,4,1,1),
        listOf(2,2,3,2,2,1,4,5) + List(94){5} + listOf(5,4),
        listOf(3,3,1,1,4,2,2,1,5) + List(94){5} + listOf(5,5,2),
        listOf(1,2,3,1,2,3,1,1,2,5,5) + List(94){5} + listOf(5,2,3,1,4,4,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,5) + List(14){5} + listOf(5),
        listOf(2,2,3,2,3,1,4,4,3,1,2,4,5) + List(13){5} + listOf(5,1,2,2,1,   5,3,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,3,2,5,5) + List(12){5} + listOf(5,5,2,   5),
        listOf(2,2,3,2,3,1,4,4,3,1,2,3,2,2,3,5) + List(12){5} + listOf(5,5,4,4,3,1,  5,2),
        listOf(3,3,1,1,4,2,2,1,4,2,1,1,2,3,4,1,5,5,5,5,5,5,5,5,5,5,5,5,4),
        listOf(1,2,3,1,2,3,1,1,2,1,2,1,2,3,4,1,4,5,5,5,5,5,5,5,5,5,5,5,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,1,2,3,4,1,2,5,5,5,5,5,5,5,5,5,2,2),
        listOf(3,3,1,1,4,2,2,1,4,2,1,1,2,3,4,1,1,2,5,5,5,5,5,5,5,5,1,2,2),
        listOf(1,2,3,1,2,3,1,1,2,1,2,1,2,3,4,1,4,2,2,5,5,5,5,5,5,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,1,2,3,4,1,2,2,2,3,5,5,5,5,4,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,1,2,3,4,1,2,2,2,3,2,5,5,4,4,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,1,2,3,4,1,2,2,2,3,2,3,1,4,4,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,2,2,1,2,3,4,1,2,3,2,3,1,4,4,3,1,2,2),
        listOf(2,2,3,2,3,1,4,4,3,1,2,2,1,2,3,4,1,2,2,3,2,3,1,4,4,3,1,2,2),
        List(100) {1}
        //62
    )

    // Tile size
    val tileSize = 73f

    val water = ImageBitmap.imageResource(R.drawable.basicwater)
    val water1 = ImageBitmap.imageResource(R.drawable.basicwater1)
    val water3 = ImageBitmap.imageResource(R.drawable.basicwater2)
    val water4 = ImageBitmap.imageResource(R.drawable.basicwater3)

    val deflaut_green = ImageBitmap.imageResource(R.drawable.deflaut_green)



    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    cameraX -= dragAmount.x
                    cameraY -= dragAmount.y
                }
            }
    ){

        //drawRect(Color.Red)
        // Loop over rows and columns
        for (row in mapData.indices) {
            for (col in mapData[row].indices) {
                val image = when (mapData[row][col]) {
                    1 -> water
                    2 -> water1
                    3 -> water3
                    4 -> water4
                    5 -> deflaut_green
                    else -> water
                }



//                val x = (col * tileSize).toInt().toFloat()
//                val y = (row * tileSize).toInt().toFloat()

                val x = (col * tileSize - cameraX).toInt().toFloat()
                val y = (row * tileSize - cameraY).toInt().toFloat()

                drawImage(
                    image = image,
                    topLeft = Offset(x, y)
                )




            }

        }
    }

}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewWorldMapSubscreen() {
    WorldMapSubscreen(onNavigateToProfile = {})
}
package com.example.organivy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // i added colour
import androidx.compose.ui.tooling.preview.Preview
import com.example.organivy.ui.theme.OrganIvyTheme

class MainActivity : ComponentActivity() {

    //NEEDS TO BE B4 ON CREATE FOR IT START AT THE SAME TIME???
    private val requestPermissionLauncher =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                logScanImages()
            }
        }
    // ENDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // INSIDE ON CREATE B4 SET CONTENT
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        // ENDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD

        setContent {
            OrganIvyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFFAEDCD) // <-- set background here
                    ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    } // END OF ON CREATE



    //SINGLE IMAGE FUNCTION ------------------------------------------------
    //DISPLAY_NAME, SIZE, DATE_TAKEN, DATE_ADDED, RELATIVE_PATH, WIDTH, HEIGHT, MIME_TYPE
    // Prints the pic name so the logcat thingy.
    fun logScanImages(){
        // says the column
        val projection = arrayOf(
            android.provider.MediaStore.Images.Media.DISPLAY_NAME
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.RELATIVE_PATH
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.SIZE
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.DATE_TAKEN
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.DATE_ADDED
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.WIDTH
        ) + arrayOf (
            android.provider.MediaStore.Images.Media.HEIGHT
        )

        val sortOrder = android.provider.MediaStore.Images.Media.DATE_ADDED + " DESC"

        val cursor = contentResolver.query(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null, null, sortOrder,


            )

        cursor?.use{ cursorObject ->
            android.util.Log.d("PHOTO_TEST", "Cursor count: ${cursorObject.count}")

            if(cursorObject.moveToFirst()){
                val nameIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.DISPLAY_NAME
                )
                val aPathIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.RELATIVE_PATH
                )
                val sizeIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.SIZE
                )
                val dTakenIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.DATE_TAKEN
                )
                val dAddedIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.DATE_ADDED
                )
                val widthIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.WIDTH
                )
                val heightIndex = cursorObject. getColumnIndexOrThrow(
                    android.provider.MediaStore.Images.Media.HEIGHT
                )


                val imageName = cursorObject.getString(nameIndex)
                val imagePath = cursorObject.getString(aPathIndex)
                val imageSize = cursorObject.getLong(sizeIndex)
                val imageDTaken = cursorObject.getString(dTakenIndex)
                val imageDAdded = cursorObject.getString(dAddedIndex)
                val imageWidth = cursorObject.getInt(widthIndex)
                val imageHeight = cursorObject.getInt(heightIndex)


                android.util.Log.d("PHOTO_TEST", "Image name: $imageName size: $imageSize date taken: $imageDTaken date added: $imageDAdded Image path: $imagePath  width: $imageWidth height: $imageHeight")


            } // end of move to first object :)

            //DECLARED FOR THE WHILE LOOP BELOW
            val nameIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.DISPLAY_NAME
            )
            val aPathIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.RELATIVE_PATH
            )
            val sizeIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.SIZE
            )
            val dTakenIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.DATE_TAKEN
            )
            val dAddedIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.DATE_ADDED
            )
            val widthIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.WIDTH
            )
            val heightIndex = cursorObject. getColumnIndexOrThrow(
                android.provider.MediaStore.Images.Media.HEIGHT
            )

            while(cursorObject.moveToNext()){
                // ^ LOOPTIE LOOP
                val imageName = cursorObject.getString(nameIndex)
                val imagePath = cursorObject.getString(aPathIndex)
                val imageSize = cursorObject.getLong(sizeIndex)
                val imageDTaken = cursorObject.getString(dTakenIndex)
                val imageDAdded = cursorObject.getString(dAddedIndex)
                val imageWidth = cursorObject.getInt(widthIndex)
                val imageHeight = cursorObject.getInt(heightIndex)

                android.util.Log.d("PHOTO_TEST", "Image name: $imageName size: $imageSize date taken: $imageDTaken date added: $imageDAdded Image path: $imagePath  width: $imageWidth height: $imageHeight")

            }
        }
    }
    // SINGLE IMAGE FUNCTION END --------------------------------

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OrganIvyTheme {
        Scaffold(containerColor = Color(0xFFFAEDCD)) {
            Greeting("Android",  modifier = Modifier.padding(it))
        }
    }
}
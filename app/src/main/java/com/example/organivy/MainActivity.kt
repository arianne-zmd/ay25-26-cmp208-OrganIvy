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
                val scanner = PhotoScanner(this)  // <-- i changed this
                val photo = scanner.logScanImages()       // <-- and this
                val largePics = photo.filter {pic ->      // <-- and this
                    pic.size >= 500000 }
                val oldPics = photo.filter {pic ->
                    pic.dateAdded <= (System.currentTimeMillis()/1000) - 31_556_952L
                }
                val duplicates = photo.groupBy{ pic ->
                    pic.size
                }
                val duplicatedPics = duplicates.filter { pic ->
                    pic.value.size > 1
                }

                /*        TEMP COMMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // logging big pics
                android.util.Log.d("PHOTO_TEST", "Found ${largePics.size} large images")
                largePics.forEach {
                    android.util.Log.d("PHOTO_TEST", "$it")
                }

                // logging old pics
                android.util.Log.d("PHOTO_TEST", "Found ${oldPics.size} old images")
                oldPics.forEach {
                    android.util.Log.d("PHOTO_TEST", "$it")
                }

                // logging duplicated picS
                android.util.Log.d("PHOTO_TEST", "Found ${duplicatedPics.size} duplicated images")
                duplicatedPics.forEach {
                    android.util.Log.d("PHOTO_TEST", "$it")
                }
                 */

                // FOR REAL PHONE
                android.util.Log.d("PHOTO_TEST", "Total photos: ${photo.size}")
                android.util.Log.d("PHOTO_TEST", "Large photos: ${largePics.size}")
                android.util.Log.d("PHOTO_TEST", "Old photos: ${oldPics.size}")
                android.util.Log.d("PHOTO_TEST", "Duplicate groups: ${duplicatedPics.size}")
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
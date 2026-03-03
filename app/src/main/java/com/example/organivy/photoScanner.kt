package com.example.organivy

import android.content.Context
import android.provider.MediaStore
import kotlin.collections.plus

class PhotoScanner (private val context: Context) {
    // photo scanning function

    //SINGLE IMAGE FUNCTION ------------------------------------------------
    //DISPLAY_NAME, SIZE, DATE_TAKEN, DATE_ADDED, RELATIVE_PATH, WIDTH, HEIGHT, MIME_TYPE
    // Prints the pic name so the logcat thingy.
    fun logScanImages(): List<Photo> {
        val photoList = mutableListOf<Photo>()
        // says the column
        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME
        ) + arrayOf(
            MediaStore.Images.Media.RELATIVE_PATH
        ) + arrayOf(
            MediaStore.Images.Media.SIZE
        ) + arrayOf(
            MediaStore.Images.Media.DATE_TAKEN
        ) + arrayOf(
            MediaStore.Images.Media.DATE_ADDED
        ) + arrayOf(
            MediaStore.Images.Media.WIDTH
        ) + arrayOf(
            MediaStore.Images.Media.HEIGHT
        )

        val sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null, null, sortOrder,


            )

        cursor?.use { cursorObject ->
            android.util.Log.d("PHOTO_TEST", "Cursor count: ${cursorObject.count}")

            // i deleted code from here

            //DECLARED THE THE WHILE LOOP BELOW
            val nameIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.DISPLAY_NAME
            )
            val aPathIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.RELATIVE_PATH
            )
            val sizeIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.SIZE
            )
            val dTakenIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.DATE_TAKEN
            )
            val dAddedIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.DATE_ADDED
            )
            val widthIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.WIDTH
            )
            val heightIndex = cursorObject.getColumnIndexOrThrow(
                MediaStore.Images.Media.HEIGHT
            )

            while (cursorObject.moveToNext()) {
                // where i take it from


                val imageName = cursorObject.getString(nameIndex)
                val imagePath = cursorObject.getString(aPathIndex)
                val imageSize = cursorObject.getLong(sizeIndex)
                val imageDTaken = cursorObject.getLong(dTakenIndex)
                val imageDAdded = cursorObject.getLong(dAddedIndex)
                val imageWidth = cursorObject.getInt(widthIndex)
                val imageHeight = cursorObject.getInt(heightIndex)

                val photo = Photo(
                    name = imageName,
                    path = imagePath,
                    size = imageSize,
                    dateTaken = cursorObject.getLong(dTakenIndex),
                    dateAdded = cursorObject.getLong(dAddedIndex),
                    width = imageWidth,
                    height = imageHeight
                )

                photoList.add(photo)

                // temp comment CHANGE BACK LATTER maybe
                //android.util.Log.d("PHOTO_TEST", "Image name: $imageName size: $imageSize date taken: $imageDTaken date added: $imageDAdded Image path: $imagePath  width: $imageWidth height: $imageHeight")

            }
        }
        // put the return statement here
        return photoList
    }
}
// SINGLE IMAGE FUNCTION END --------------------------------

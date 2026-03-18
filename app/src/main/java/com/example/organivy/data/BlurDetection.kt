package com.example.organivy.data


import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.graphics.ImageDecoder
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import kotlin.math.abs

class BlurDetection (private val context: Context) {
    // yh idk hwat im doing
    // bitmap stuff


    fun isImageBlurry(photo: Photo): Boolean {

        val uri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            photo.id
        )

        //Read image sizee
        val options = Options()
        options.inJustDecodeBounds = true

        var inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream?.close()

        // sample size
        options.inSampleSize = calculateSampleSize(options, 500, 500)
        options.inJustDecodeBounds = false

        //checking for blur?
        inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        inputStream?.close()

        if (bitmap == null) return false

        val sharpness = calculateSharpness(bitmap)

        android.util.Log.d("BLUR_TEST", "Sharpness score: $sharpness")

        bitmap.recycle()

        return sharpness < 2
    }

    fun calculateSampleSize(options: Options, reqWidth: Int, reqHeight: Int): Int {

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun calculateSharpness(bitmap: Bitmap): Double {

        val width = bitmap.width
        val height = bitmap.height

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        var totalDifference = 0L
        var count = 0

        for (y in 0 until height) {
            for (x in 0 until width - 1) {

                val index = y * width + x

                val pixel = pixels[index]
                val rightPixel = pixels[index + 1]

                val gray1 = grayScale(pixel)
                val gray2 = grayScale(rightPixel)

                totalDifference += abs(gray1 - gray2)
                count++
            }
        }

        return if (count == 0) 0.0 else totalDifference.toDouble() / count
    }

    private fun grayScale(pixel: Int): Int {
        val r = (pixel shr 16) and 0xff
        val g = (pixel shr 8) and 0xff
        val b = pixel and 0xff
        return (0.3 * r + 0.59 * g + 0.11 * b).toInt()
    }
}
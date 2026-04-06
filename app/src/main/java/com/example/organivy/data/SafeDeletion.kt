package com.example.organivy.data

import android.app.RecoverableSecurityException
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest

class SafeDeletion (private val context: Context){

    fun photoUri(photo: Photo): Uri {

        return ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            photo.id
        )
    }

    fun deletePhotoSafely(
        photoUri: Uri,
        deleteResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {

        fun deleteImageAPI29(context: Context, uri: Uri) {
            val resolver: ContentResolver = context.contentResolver
            try {
                resolver.delete(uri, null, null)
            } catch (securityException: SecurityException) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    val recoverableSecurityException =
                        securityException as RecoverableSecurityException

                    val senderRequest = IntentSenderRequest.Builder(
                        recoverableSecurityException.userAction
                            .actionIntent
                            .intentSender
                    ).build()

                    deleteResultLauncher.launch(senderRequest)
                }
            }
        }

        fun deleteImageAPI30(context: Context, uri: Uri) {

            val contentResolver: ContentResolver = context.contentResolver
            val uriList: ArrayList<Uri> = ArrayList()

            uriList.add(uri)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                val intentSender: IntentSender =
                    MediaStore.createDeleteRequest(contentResolver, uriList).intentSender

                val senderRequest = IntentSenderRequest.Builder(intentSender)
                    .setFillInIntent(null)
                    .setFlags(0, 0)
                    .build()

                deleteResultLauncher.launch(senderRequest)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            deleteImageAPI30(context, photoUri)
        } else {
            deleteImageAPI29(context, photoUri)
        }
    }
}
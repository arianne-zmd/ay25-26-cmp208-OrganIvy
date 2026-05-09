package com.example.organivy.data

import android.os.Build
import android.provider.Settings
import android.content.Context
import kotlin.String

data class DeviceInfo(
    val deviceID: String,
    val manufacturer : String,
    val model: String,
    val device: String,
    val hardware: String,
    val version : String,
    val sdkInt : Int,
)

/*
val manufacturer = Build.MANUFACTURER,
val model = Build.MODEL,
val device = Build.DEVICE,
val hardware = Build.HARDWARE,
val version = Build.VERSION.RELEASE,
val sdkInt = Build.VERSION.SDK_INT


fun getAndroid(context: Context): String {
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

*/
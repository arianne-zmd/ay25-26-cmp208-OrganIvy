package com.example.organivy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.organivy.R
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)



val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto Flex"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Lato"),
        fontProvider = provider,
    )
)

val pixelFontFamily = FontFamily(
    Font(R.font.pixelifysans_regular, weight = FontWeight.Normal)
)




// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    //  Pixel UI text (headings, titles)
    displayLarge = baseline.displayLarge.copy(fontFamily = pixelFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = pixelFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = pixelFontFamily),

    headlineLarge = baseline.headlineLarge.copy(fontFamily = pixelFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = pixelFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = pixelFontFamily),

    titleLarge = baseline.titleLarge.copy(fontFamily = pixelFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = pixelFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = pixelFontFamily),

    //readable text stays normal
    bodyLarge = baseline.bodyLarge.copy(fontFamily = displayFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = displayFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = displayFontFamily),

    labelLarge = baseline.labelLarge.copy(fontFamily = displayFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = displayFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = displayFontFamily),
)

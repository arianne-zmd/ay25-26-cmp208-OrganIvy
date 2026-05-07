package com.example.organivy.ui.theme

import android.graphics.Color.red
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.R
import com.example.organivy.ui.pages.HomeScreen


@Composable
fun OrganicCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFF3EFE6), // light top
                        Color( 0xFFFF0000 )  // darker bottom
                    )
                )
            )
            .border(
                width = 3.dp,
                color = Color(0xFFD6CFC2),
                shape = shape
            )
            .border(
                width = 6.dp,
                color = Color(0xFFFF0000),
                shape = shape
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(16.dp)
    ) {
        Card(modifier = modifier.clip(CircleShape)
            . size(10.dp)
            .align(alignment = Alignment.TopEnd)
            ,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) { }

        Image(
            painter = painterResource(id = R.drawable.wooden_border),
            contentDescription = "Garden Image",
            modifier = Modifier
                .aspectRatio(1.5f)
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        content()
    }
}

@Preview(showBackground = true)
@Preview(device = Devices.PIXEL_7)
@Preview(widthDp = 300, heightDp = 400)
@Composable
fun PreviewOrganicCard() {
    OrganicCard(
       content = {}
    )
}

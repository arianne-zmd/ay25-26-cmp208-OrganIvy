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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.R
import com.example.organivy.ui.pages.HomeScreen

/* actually gonna cry. what do i do
* ok so garden page
* plant cycle images
* cycle logic ( if deleted pic > 100, 000 plant level 2)
* plant leveling up animation
* garden background
* */
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
            . size(20.dp)
            .align(alignment = Alignment.TopEnd)
            ,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) { }

        Card(modifier = modifier.clip(RectangleShape)
            . size(200.dp)
            //.align(Alignment.Center)
            .offset(y = (30).dp)
            .padding(5.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) { }

        Card(modifier = modifier.clip(RectangleShape)
            . size(200.dp)
            //.align(alignment = Alignment.Center)
            .offset(x = 15.dp, y = 5.dp)
            .padding(5.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) { }

        Card(modifier = modifier.clip(RectangleShape)
            . size(200.dp)
            //.align(alignment = Alignment.Center)
            .offset(x = 25.dp, y = 1.dp)
            .padding(5.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) { }

        /*
        Image(
            painter = painterResource(id = R.drawable.wooden_border),
            contentDescription = "Garden Image",
            modifier = Modifier
                .aspectRatio(1.5f)
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit//crop
        )*/
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


package com.example.outletfashion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.ui.theme.*

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(255.dp)
                .background(LuxuryLightGray)
        ) {
            AsyncImage(
                model = product.imageUrl.toIntOrNull() ?: product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (product.discount > 0) {
                Text(
                    text = "-${product.discount}%",
                    fontSize = 10.sp,
                    color = LuxuryBlack,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(9.dp)
                        .background(Color.White.copy(alpha = 0.94f))
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(9.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.94f))
                    .clickable(onClick = onFavoriteClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isFavorite) "♥" else "♡",
                    fontSize = 24.sp,
                    color = LuxuryBlack
                )
            }
        }

        Spacer(Modifier.height(11.dp))

        Text(
            text = product.brand.uppercase(),
            color = LuxuryGray,
            fontSize = 10.sp,
            letterSpacing = 1.2.sp
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = product.name,
            color = LuxuryBlack,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(7.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "%.2f lei".format(product.price),
                color = LuxuryBlack,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            if (product.oldPrice > product.price) {
                Text(
                    text = "%.2f".format(product.oldPrice),
                    color = LuxuryGray,
                    fontSize = 11.sp,
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }
}

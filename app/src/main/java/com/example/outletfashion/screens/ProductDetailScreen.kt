
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.ui.theme.*

@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToCart: (Product, String) -> Unit
) {
    var selectedSize by remember(product.id) {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clickable(onClick = onBack)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 25.sp,
                color = LuxuryBlack
            )

            Spacer(Modifier.width(14.dp))

            Text(
                text = "BACK TO SHOP",
                fontSize = 11.sp,
                letterSpacing = 1.4.sp,
                color = LuxuryBlack
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(410.dp)
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
                        color = LuxuryBlack,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .background(Color.White)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )
                    )
                }
            }

            Column(
                modifier = Modifier.padding(
                    horizontal = 22.dp,
                    vertical = 25.dp
                )
            ) {
                Text(
                    text = product.brand.uppercase(),
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    color = LuxuryGray
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = product.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Light,
                    color = LuxuryBlack,
                    lineHeight = 32.sp
                )

                Spacer(Modifier.height(17.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "%.2f LEI".format(product.price),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium,
                        color = LuxuryBlack
                    )

                    if (product.oldPrice > product.price) {
                        Text(
                            text = "%.2f LEI".format(
                                product.oldPrice
                            ),
                            fontSize = 14.sp,
                            color = LuxuryGray,
                            textDecoration =
                                TextDecoration.LineThrough
                        )
                    }
                }

                Spacer(Modifier.height(27.dp))

                HorizontalDivider(color = LuxuryBorder)

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "SELECT SIZE",
                    fontSize = 11.sp,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(15.dp))

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(9.dp)
                ) {
                    product.sizes.forEach { size ->
                        val selected = selectedSize == size

                        Box(
                            modifier = Modifier
                                .size(51.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (selected)
                                        LuxuryBlack
                                    else LuxuryBorder
                                )
                                .background(
                                    if (selected)
                                        LuxuryBlack
                                    else LuxuryWhite
                                )
                                .clickable {
                                    selectedSize = size
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = size,
                                fontSize = 12.sp,
                                color = if (selected)
                                    LuxuryWhite
                                else LuxuryBlack
                            )
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                HorizontalDivider(color = LuxuryBorder)

                Spacer(Modifier.height(23.dp))

                Text(
                    text = "PRODUCT DETAILS",
                    fontSize = 11.sp,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "A carefully selected piece from " +
                            "our contemporary fashion collection. " +
                            "Designed for effortless everyday style.",
                    fontSize = 13.sp,
                    lineHeight = 21.sp,
                    color = LuxuryGray
                )

                Spacer(Modifier.height(30.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LuxuryWhite)
                .padding(
                    horizontal = 18.dp,
                    vertical = 12.dp
                )
        ) {
            Button(
                onClick = {
                    selectedSize?.let { size ->
                        onAddToCart(product, size)
                    }
                },
                enabled = selectedSize != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryBlack,
                    contentColor = LuxuryWhite,
                    disabledContainerColor = LuxuryLightGray,
                    disabledContentColor = LuxuryGray
                ),
                shape = androidx.compose.foundation.shape
                    .RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    text = if (selectedSize == null)
                        "SELECT A SIZE"
                    else
                        "ADD TO BAG",
                    fontSize = 12.sp,
                    letterSpacing = 1.8.sp
                )
            }
        }
    }
}

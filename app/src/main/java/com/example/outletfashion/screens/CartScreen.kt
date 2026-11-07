
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.outletfashion.data.model.CartItem
import com.example.outletfashion.ui.theme.*

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onBack: () -> Unit,
    onRemove: (CartItem) -> Unit,
    onCheckout: () -> Unit
) {
    val subtotal = cartItems.sumOf {
        it.product.price * it.quantity
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 25.sp,
                color = LuxuryBlack,
                modifier = Modifier
                    .clickable(onClick = onBack)
                    .padding(end = 18.dp)
            )

            Text(
                text = "SHOPPING BAG",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp,
                color = LuxuryBlack
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "${cartItems.sumOf { it.quantity }} ITEMS",
                fontSize = 10.sp,
                color = LuxuryGray
            )
        }

        HorizontalDivider(color = LuxuryBorder)

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "YOUR BAG IS EMPTY",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Discover pieces you'll love.",
                    fontSize = 13.sp,
                    color = LuxuryGray
                )

                Spacer(Modifier.height(25.dp))

                Text(
                    text = "CONTINUE SHOPPING",
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp,
                    color = LuxuryBlack,
                    modifier = Modifier
                        .clickable(onClick = onBack)
                        .padding(12.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 18.dp
                ),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(cartItems) { item ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(14.dp)
                        ) {
                            AsyncImage(
                                model = item.product.imageUrl.toIntOrNull()
                                    ?: item.product.imageUrl,
                                contentDescription = item.product.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(112.dp)
                                    .height(145.dp)
                                    .background(LuxuryLightGray)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.product.brand.uppercase(),
                                    fontSize = 10.sp,
                                    letterSpacing = 1.2.sp,
                                    color = LuxuryGray
                                )

                                Spacer(Modifier.height(7.dp))

                                Text(
                                    text = item.product.name,
                                    fontSize = 14.sp,
                                    color = LuxuryBlack
                                )

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    text = "SIZE: ${item.size}",
                                    fontSize = 11.sp,
                                    color = LuxuryGray
                                )

                                Text(
                                    text = "QTY: ${item.quantity}",
                                    fontSize = 11.sp,
                                    color = LuxuryGray
                                )

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    text = "%.2f LEI".format(
                                        item.product.price * item.quantity
                                    ),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = LuxuryBlack
                                )

                                Spacer(Modifier.height(12.dp))

                                Text(
                                    text = "REMOVE",
                                    fontSize = 10.sp,
                                    letterSpacing = 1.sp,
                                    textDecoration =
                                        TextDecoration.Underline,
                                    color = LuxuryGray,
                                    modifier = Modifier
                                        .clickable {
                                            onRemove(item)
                                        }
                                        .padding(vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(15.dp))

                        HorizontalDivider(color = LuxuryBorder)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LuxuryWhite)
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SUBTOTAL",
                        fontSize = 12.sp,
                        letterSpacing = 1.sp,
                        color = LuxuryBlack
                    )

                    Text(
                        text = "%.2f LEI".format(subtotal),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = LuxuryBlack
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Shipping costs are shown at checkout.",
                    fontSize = 11.sp,
                    color = LuxuryGray
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = onCheckout,
                    enabled = cartItems.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LuxuryBlack,
                        contentColor = LuxuryWhite
                    ),
                    shape = androidx.compose.foundation.shape
                        .RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text(
                        text = "CONTINUE TO CHECKOUT",
                        fontSize = 12.sp,
                        letterSpacing = 1.5.sp
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Demo checkout. No payment will be processed.",
                    fontSize = 10.sp,
                    color = LuxuryGray,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
        }
    }
}


package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.data.model.CartItem
import com.example.outletfashion.ui.theme.*

@Composable
fun CheckoutScreen(
    cartItems: List<CartItem>,
    onBack: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var deliveryMethod by remember { mutableStateOf("Curier") }
    var orderPreview by remember { mutableStateOf(false) }

    val subtotal = cartItems.sumOf {
        it.product.price * it.quantity
    }

    val shipping = if (deliveryMethod == "Curier") 19.90 else 0.0
    val total = subtotal + shipping

    val formValid = fullName.isNotBlank() &&
            phone.isNotBlank() &&
            email.isNotBlank() &&
            city.isNotBlank() &&
            address.isNotBlank() &&
            postalCode.isNotBlank() &&
            cartItems.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
    ) {
        TextButton(onClick = onBack) {
            Text("← BACK TO BAG", color = LuxuryBlack)
        }

        HorizontalDivider(color = LuxuryBorder)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                "CHECKOUT",
                fontSize = 27.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp,
                color = LuxuryBlack
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Complete your delivery details",
                fontSize = 13.sp,
                color = LuxuryGray
            )

            Spacer(Modifier.height(28.dp))

            CheckoutHeading("CONTACT INFORMATION")

            CheckoutField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full name"
            )

            CheckoutField(
                value = email,
                onValueChange = { email = it },
                label = "Email address"
            )

            CheckoutField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone number"
            )

            Spacer(Modifier.height(24.dp))

            CheckoutHeading("DELIVERY ADDRESS")

            CheckoutField(
                value = city,
                onValueChange = { city = it },
                label = "City"
            )

            CheckoutField(
                value = address,
                onValueChange = { address = it },
                label = "Street and number"
            )

            CheckoutField(
                value = postalCode,
                onValueChange = { postalCode = it },
                label = "Postal code"
            )

            Spacer(Modifier.height(24.dp))

            CheckoutHeading("DELIVERY METHOD")

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = deliveryMethod == "Curier",
                    onClick = { deliveryMethod = "Curier" }
                )
                Text(
                    "Courier delivery",
                    fontSize = 13.sp,
                    color = LuxuryBlack
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = deliveryMethod == "Ridicare",
                    onClick = { deliveryMethod = "Ridicare" }
                )
                Text(
                    "Store pickup (demo)",
                    fontSize = 13.sp,
                    color = LuxuryBlack
                )
            }

            Spacer(Modifier.height(24.dp))

            CheckoutHeading("ORDER SUMMARY")

            cartItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${item.product.name} (${item.size}) x${item.quantity}",
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp,
                        color = LuxuryGray
                    )

                    Text(
                        "%.2f LEI".format(
                            item.product.price * item.quantity
                        ),
                        fontSize = 12.sp,
                        color = LuxuryBlack
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = LuxuryBorder
            )

            SummaryRow("Subtotal", subtotal)
            SummaryRow("Shipping (demo)", shipping)

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "TOTAL",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxuryBlack
                )

                Text(
                    "%.2f LEI".format(total),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxuryBlack
                )
            }

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = { orderPreview = true },
                enabled = formValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = androidx.compose.foundation.shape
                    .RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryBlack,
                    contentColor = LuxuryWhite
                )
            ) {
                Text(
                    "PREVIEW ORDER",
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Demo only. No order or payment will be processed.",
                fontSize = 11.sp,
                color = LuxuryGray
            )
        }
    }

    if (orderPreview) {
        AlertDialog(
            onDismissRequest = { orderPreview = false },
            title = { Text("Order preview") },
            text = {
                Text(
                    "Name: $fullName\n" +
                            "City: $city\n" +
                            "Delivery: $deliveryMethod\n" +
                            "Total: %.2f LEI\n\n".format(total) +
                            "This is a demonstration. " +
                            "No real order has been placed."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { orderPreview = false }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun CheckoutHeading(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.4.sp,
        color = LuxuryBlack,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun CheckoutField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LuxuryBlack,
            unfocusedBorderColor = LuxuryBorder
        )
    )
}

@Composable
private fun SummaryRow(
    label: String,
    amount: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 12.sp, color = LuxuryGray)
        Text(
            "%.2f LEI".format(amount),
            fontSize = 12.sp,
            color = LuxuryBlack
        )
    }
}


package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.outletfashion.ui.theme.*

@Composable
fun AccountScreen(
    favoriteCount: Int,
    onFavoritesClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var editingProfile by remember { mutableStateOf(false) }
    var selectedSection by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 26.dp)
    ) {
        Text(
            text = "MY ACCOUNT",
            fontSize = 29.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp,
            color = LuxuryBlack
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Your personal fashion space.",
            fontSize = 13.sp,
            color = LuxuryGray
        )

        Spacer(Modifier.height(30.dp))

        HorizontalDivider(color = LuxuryBorder)

        Spacer(Modifier.height(25.dp))

        Text(
            text = if (fullName.isBlank()) {
                "WELCOME TO OUTLET GALAXY"
            } else {
                "HELLO, ${fullName.uppercase()}"
            },
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp,
            color = LuxuryBlack
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Manage your profile and shopping preferences.",
            fontSize = 12.sp,
            color = LuxuryGray
        )

        Spacer(Modifier.height(32.dp))

        AccountMenuItem(
            title = "PERSONAL DETAILS",
            subtitle = "Name, email and phone",
            onClick = { editingProfile = true }
        )

        AccountMenuItem(
            title = "MY ORDERS",
            subtitle = "Your order history",
            onClick = { selectedSection = "MY ORDERS" }
        )

        AccountMenuItem(
            title = "MY FAVORITES",
            subtitle = "$favoriteCount saved items",
            onClick = onFavoritesClick
        )

        AccountMenuItem(
            title = "DELIVERY ADDRESSES",
            subtitle = "Manage your addresses",
            onClick = {
                selectedSection = "DELIVERY ADDRESSES"
            }
        )

        AccountMenuItem(
            title = "CUSTOMER SUPPORT",
            subtitle = "Help and contact",
            onClick = {
                selectedSection = "CUSTOMER SUPPORT"
            }
        )

        Spacer(Modifier.height(38.dp))

        HorizontalDivider(color = LuxuryBorder)

        Spacer(Modifier.height(20.dp))

        Text(
            text = "OUTLET GALAXY",
            fontSize = 13.sp,
            letterSpacing = 2.sp,
            color = LuxuryBlack
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Fashion. Discoveries. You.",
            fontSize = 11.sp,
            color = LuxuryGray
        )
    }

    if (editingProfile) {
        AlertDialog(
            onDismissRequest = { editingProfile = false },
            title = {
                Text("PERSONAL DETAILS")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { editingProfile = false }
                ) {
                    Text("SAVE", color = LuxuryBlack)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { editingProfile = false }
                ) {
                    Text("CLOSE", color = LuxuryGray)
                }
            }
        )
    }

    if (selectedSection != null) {
        AlertDialog(
            onDismissRequest = { selectedSection = null },
            title = {
                Text(selectedSection ?: "")
            },
            text = {
                Text(
                    when (selectedSection) {
                        "MY ORDERS" ->
                            "No orders yet. Order history will be available in a future version."

                        "DELIVERY ADDRESSES" ->
                            "Saved delivery addresses will be available soon."

                        else ->
                            "Customer support options will be added soon."
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { selectedSection = null }
                ) {
                    Text("OK", color = LuxuryBlack)
                }
            }
        )
    }
}

@Composable
private fun AccountMenuItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 19.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = LuxuryGray
                )
            }

            Text(
                text = "›",
                fontSize = 25.sp,
                color = LuxuryGray
            )
        }
    }

    HorizontalDivider(color = LuxuryBorder)
}

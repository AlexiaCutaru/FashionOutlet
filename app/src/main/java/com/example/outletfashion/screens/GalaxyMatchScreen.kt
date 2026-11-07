
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.ui.theme.*


@Composable
fun GalaxyMatchScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onBack: () -> Unit
) {
    var category by remember { mutableStateOf("Femei") }
    var style by remember { mutableStateOf("Minimalist") }
    var budget by remember { mutableFloatStateOf(500f) }
    var showResults by remember { mutableStateOf(false) }

    val selectedTypes = when (style) {
        "Elegant" -> listOf(
            "Sacouri", "Rochii", "Camasi",
            "Pantaloni", "Paltoane"
        )
        "Casual" -> listOf(
            "Tricouri", "Jeans", "Pulovere",
            "Hanorace", "Jachete"
        )
        else -> listOf(
            "Sacouri", "Camasi", "Pantaloni",
            "Pulovere", "Tricouri"
        )
    }

    val recommendations = products
        .filter {
            it.category == category &&
                    it.price <= budget &&
                    selectedTypes.any { type ->
                        it.type.equals(type, ignoreCase = true)
                    }
        }
        .sortedBy { it.price }
        .take(8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
    ) {
        TextButton(onClick = onBack) {
            Text("← BACK", color = LuxuryBlack)
        }

        HorizontalDivider(color = LuxuryBorder)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(22.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Text(
                    text = "GALAXY MATCH",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Discover fashion selected for you.",
                    fontSize = 13.sp,
                    color = LuxuryGray
                )
            }

            if (!showResults) {
                item {
                    MatchHeading("01 / SELECT CATEGORY")

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {
                        MatchChoice(
                            title = "WOMAN",
                            selected = category == "Femei",
                            modifier = Modifier.weight(1f),
                            onClick = { category = "Femei" }
                        )

                        MatchChoice(
                            title = "MAN",
                            selected = category == "Barbati",
                            modifier = Modifier.weight(1f),
                            onClick = { category = "Barbati" }
                        )
                    }
                }

                item {
                    MatchHeading("02 / YOUR STYLE")

                    listOf(
                        "Minimalist",
                        "Elegant",
                        "Casual"
                    ).forEach { option ->
                        MatchChoice(
                            title = option.uppercase(),
                            selected = style == option,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { style = option }
                        )

                        Spacer(Modifier.height(8.dp))
                    }
                }

                item {
                    MatchHeading("03 / YOUR BUDGET")

                    Text(
                        text = "${budget.toInt()} LEI",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light,
                        color = LuxuryBlack
                    )

                    Slider(
                        value = budget,
                        onValueChange = { budget = it },
                        valueRange = 100f..1500f,
                        colors = SliderDefaults.colors(
                            thumbColor = LuxuryBlack,
                            activeTrackColor = LuxuryBlack
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {
                        Text("100 LEI", fontSize = 11.sp)
                        Text("1500 LEI", fontSize = 11.sp)
                    }
                }

                item {
                    Button(
                        onClick = { showResults = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LuxuryBlack,
                            contentColor = LuxuryWhite
                        )
                    ) {
                        Text(
                            "FIND MY STYLE",
                            letterSpacing = 1.5.sp
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "YOUR PERSONAL EDIT",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = 1.5.sp,
                        color = LuxuryBlack
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "$style · $category · Under ${budget.toInt()} LEI per item",
                        fontSize = 12.sp,
                        color = LuxuryGray
                    )

                    Spacer(Modifier.height(10.dp))

                    TextButton(
                        onClick = { showResults = false }
                    ) {
                        Text("EDIT PREFERENCES", color = LuxuryBlack)
                    }
                }

                if (recommendations.isEmpty()) {
                    item {
                        Text(
                            "No matching products. Try another budget or style.",
                            color = LuxuryGray,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    items(recommendations) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onProductClick(product)
                                }
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                product.brand.uppercase(),
                                fontSize = 11.sp,
                                color = LuxuryGray
                            )

                            Spacer(Modifier.height(5.dp))

                            Text(
                                product.name,
                                fontSize = 16.sp,
                                color = LuxuryBlack
                            )

                            Spacer(Modifier.height(5.dp))

                            Text(
                                "%.2f LEI".format(product.price),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = LuxuryBlack
                            )

                            Spacer(Modifier.height(10.dp))

                            HorizontalDivider(color = LuxuryBorder)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchHeading(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.3.sp,
        color = LuxuryBlack,
        modifier = Modifier.padding(bottom = 14.dp)
    )
}

@Composable
private fun MatchChoice(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                if (selected) LuxuryBlack else LuxuryWhite
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            color = if (selected) LuxuryWhite else LuxuryBlack
        )
    }
}

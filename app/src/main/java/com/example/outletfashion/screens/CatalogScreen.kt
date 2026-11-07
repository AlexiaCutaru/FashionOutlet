
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.data.model.sampleProducts
import com.example.outletfashion.ui.components.ProductCard
import com.example.outletfashion.ui.theme.*

@Composable
fun CatalogScreen(
    category: String,
    onProductClick: (Product) -> Unit,
    favoriteIds: List<Int> = emptyList(),
    onFavoriteClick: (Product) -> Unit = {}
) {
    var selectedCategory by remember(category) {
        mutableStateOf(
            if (category == "Barbati") "Barbati" else "Femei"
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf("Toate") }
    var selectedType by remember { mutableStateOf("Toate") }
    var sortBy by remember { mutableStateOf("Recomandate") }

    val genderProducts = sampleProducts.filter {
        it.category == selectedCategory
    }

    val brands = listOf("Toate") +
            genderProducts.map { it.brand }.distinct().sorted()

    val types = listOf("Toate") +
            genderProducts.map { it.type }.distinct().sorted()

    val filteredProducts = genderProducts
        .filter { product ->
            (searchQuery.isBlank() ||
                    product.name.contains(searchQuery, ignoreCase = true) ||
                    product.brand.contains(searchQuery, ignoreCase = true)) &&
                    (selectedBrand == "Toate" ||
                            product.brand == selectedBrand) &&
                    (selectedType == "Toate" ||
                            product.type == selectedType)
        }
        .let { products ->
            when (sortBy) {
                "Pret crescator" -> products.sortedBy { it.price }
                "Pret descrescator" -> products.sortedByDescending { it.price }
                "Reducere" -> products.sortedByDescending { it.discount }
                else -> products
            }
        }

    LaunchedEffect(selectedCategory) {
        selectedBrand = "Toate"
        selectedType = "Toate"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite)
    ) {
        // SELECTOR FEMEI / BARBATI
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            listOf("Femei", "Barbati").forEach { gender ->
                val selected = selectedCategory == gender

                Column(
                    modifier = Modifier.clickable {
                        selectedCategory = gender
                        searchQuery = ""
                        selectedBrand = "Toate"
                        selectedType = "Toate"
                    }
                ) {
                    Text(
                        text = if (gender == "Femei") "WOMAN" else "MAN",
                        fontSize = 15.sp,
                        fontWeight = if (selected)
                            FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selected)
                            LuxuryBlack else LuxuryGray,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(Modifier.height(7.dp))

                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(2.dp)
                            .background(
                                if (selected)
                                    LuxuryBlack else LuxuryWhite
                            )
                    )
                }
            }
        }

        // SEARCH
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    "Search products or brands...",
                    fontSize = 13.sp
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LuxuryBlack,
                unfocusedBorderColor = LuxuryBorder
            )
        )

        Spacer(Modifier.height(12.dp))

        // FILTERS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CatalogDropdown(
                    selected = selectedBrand,
                    options = brands,
                    onSelected = { selectedBrand = it },
                    label = "BRAND"
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                CatalogDropdown(
                    selected = selectedType,
                    options = types,
                    onSelected = { selectedType = it },
                    label = "TYPE"
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                CatalogDropdown(
                    selected = sortBy,
                    options = listOf(
                        "Recomandate",
                        "Pret crescator",
                        "Pret descrescator",
                        "Reducere"
                    ),
                    onSelected = { sortBy = it },
                    label = "SORT"
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        Text(
            text = "${filteredProducts.size} PRODUCTS",
            fontSize = 11.sp,
            letterSpacing = 1.3.sp,
            color = LuxuryGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                bottom = 30.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            if (filteredProducts.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "No products found.",
                        fontSize = 14.sp,
                        color = LuxuryGray,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }

            items(
                items = filteredProducts,
                key = { it.id }
            ) { product ->
                ProductCard(
                    product = product,
                    isFavorite = favoriteIds.contains(product.id),
                    onFavoriteClick = {
                        onFavoriteClick(product)
                    },
                    onClick = {
                        onProductClick(product)
                    }
                )
            }
        }
    }
}

@Composable
private fun CatalogDropdown(
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = label,
                fontSize = 9.sp,
                color = LuxuryGray,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "$selected ▾",
                fontSize = 11.sp,
                color = LuxuryBlack,
                maxLines = 1
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(option, fontSize = 13.sp)
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

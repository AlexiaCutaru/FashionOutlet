
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.ui.components.ProductCard
import com.example.outletfashion.ui.theme.*

@Composable
fun FavoritesScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite),
        contentPadding = PaddingValues(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Column {
                Text(
                    "SAVED ITEMS",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "${products.size} produse salvate",
                    fontSize = 12.sp,
                    color = LuxuryGray
                )
            }
        }

        if (products.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    "Nu ai adăugat încă produse la favorite.",
                    color = LuxuryGray,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }

        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                isFavorite = true,
                onFavoriteClick = { onFavoriteClick(product) },
                onClick = { onProductClick(product) }
            )
        }
    }
}

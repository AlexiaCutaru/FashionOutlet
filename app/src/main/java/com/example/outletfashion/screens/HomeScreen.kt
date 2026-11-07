
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.outletfashion.data.model.Product
import com.example.outletfashion.data.model.sampleProducts
import com.example.outletfashion.ui.components.ProductCard
import com.example.outletfashion.ui.theme.*

@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    favoriteIds: List<Int> = emptyList(),
    onFavoriteClick: (Product) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            bottom = 35.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        // HERO EDITORIAL
        item(span = { GridItemSpan(2) }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1539109136881-3be0616acf4b?w=1200",
                    contentDescription = "Noua colecție Outlet Galaxy",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(480.dp)
                        .background(LuxuryLightGray)
                )

                Spacer(Modifier.height(27.dp))

                Text(
                    text = "THE NEW EDITION",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.5.sp,
                    color = LuxuryBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(9.dp))

                Text(
                    text = "Contemporary pieces. Timeless style.",
                    fontSize = 12.sp,
                    color = LuxuryGray,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(21.dp))

                Text(
                    text = "EXPLORE COLLECTION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack,
                    modifier = Modifier
                        .clickable {
                            onCategoryClick("Femei")
                        }
                        .padding(vertical = 12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.width(145.dp),
                    color = LuxuryBlack,
                    thickness = 1.dp
                )
            }
        }

        // CATEGORII
        item(span = { GridItemSpan(2) }) {
            EditorialHeading("SHOP BY CATEGORY")
        }

        item {
            EditorialCategory(
                title = "WOMAN",
                imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=800",
                onClick = { onCategoryClick("Femei") }
            )
        }

        item {
            EditorialCategory(
                title = "MAN",
                imageUrl = "https://images.unsplash.com/photo-1617127365659-c47fa864d8bc?w=800",
                onClick = { onCategoryClick("Barbati") }
            )
        }

        // PRODUSE
        item(span = { GridItemSpan(2) }) {
            EditorialHeading("THE SELECTION")
        }

        items(
            items = sampleProducts,
            key = { it.id }
        ) { product ->
            ProductCard(
                product = product,
                isFavorite = favoriteIds.contains(product.id),
                onFavoriteClick = { onFavoriteClick(product) },
                onClick = { onProductClick(product) }
            )
        }

        // GALAXY CLUB
        item(span = { GridItemSpan(2) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LuxuryLightGray)
                    .clickable {
                        onCategoryClick("Galaxy Club")
                    }
                    .padding(
                        horizontal = 22.dp,
                        vertical = 45.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "OUTLET GALAXY",
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    color = LuxuryGray
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    "THE GALAXY CLUB",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    "An exclusive space for our community.",
                    fontSize = 12.sp,
                    color = LuxuryGray,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(23.dp))

                Text(
                    "DISCOVER MORE",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = LuxuryBlack
                )
            }
        }
    }
}

@Composable
private fun EditorialHeading(
    title: String
) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 21.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 2.sp,
            color = LuxuryBlack
        )

        Spacer(Modifier.height(10.dp))

        HorizontalDivider(
            color = LuxuryBorder,
            thickness = 1.dp
        )
    }
}

@Composable
private fun EditorialCategory(
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(LuxuryLightGray)
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp,
            color = LuxuryBlack
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "DISCOVER",
            fontSize = 10.sp,
            letterSpacing = 1.sp,
            color = LuxuryGray
        )
    }
}

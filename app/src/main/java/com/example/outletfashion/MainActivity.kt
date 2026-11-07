
package com.example.outletfashion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.data.model.*
import com.example.outletfashion.ui.components.BottomNavBar
import com.example.outletfashion.ui.screens.*
import com.example.outletfashion.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OutletFashionTheme {
                OutletGalaxyRoot()
            }
        }
    }
}

@Composable
fun OutletGalaxyRoot() {
    val auth = remember { FirebaseAuth.getInstance() }

    var currentUser by remember {
        mutableStateOf<FirebaseUser?>(auth.currentUser)
    }

    var showRegister by remember {
        mutableStateOf(false)
    }

    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
        }

        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    when {
        currentUser != null -> {
            key(currentUser?.uid) {
                OutletGalaxyApp(
                    onLogout = {
                        auth.signOut()
                        currentUser = null
                        showRegister = false
                    }
                )
            }
        }

        showRegister -> {
            RegisterScreen(
                onRegisterSuccess = {
                    currentUser = auth.currentUser
                },
                onLoginClick = {
                    showRegister = false
                }
            )
        }

        else -> {
            LoginScreen(
                onLoginSuccess = {
                    currentUser = auth.currentUser
                },
                onRegisterClick = {
                    showRegister = true
                }
            )
        }
    }
}

@Composable
fun OutletGalaxyApp(
    onLogout: () -> Unit
) {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    var selectedCategory by remember {
        mutableStateOf("Femei")
    }

    var selectedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    var showCart by remember {
        mutableStateOf(false)
    }

    var showCheckout by remember {
        mutableStateOf(false)
    }

    var showGalaxyVision by remember {
        mutableStateOf(false)
    }

    var showMyOutfits by remember {
        mutableStateOf(false)
    }

    var showShakeDiscover by remember {
        mutableStateOf(false)
    }

    val cartItems = remember {
        mutableStateListOf<CartItem>()
    }

    val favoriteIds = remember {
        mutableStateListOf<Int>()
    }

    fun toggleFavorite(product: Product) {
        if (favoriteIds.contains(product.id)) {
            favoriteIds.remove(product.id)
        } else {
            favoriteIds.add(product.id)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LuxuryWhite,

        topBar = {
            if (
                !showCart &&
                !showCheckout &&
                !showGalaxyVision &&
                !showMyOutfits &&
                !showShakeDiscover &&
                selectedProduct == null
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LuxuryWhite)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "OUTLET GALAXY",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = 2.sp,
                        color = LuxuryBlack
                    )

                    TextButton(
                        onClick = {
                            showCart = true
                            showCheckout = false
                        }
                    ) {
                        Text(
                            text = "BAG (${cartItems.sumOf { it.quantity }})",
                            fontSize = 11.sp,
                            color = LuxuryBlack
                        )
                    }
                }
            }
        },

        bottomBar = {
            if (
                !showCart &&
                !showCheckout &&
                !showGalaxyVision &&
                !showMyOutfits &&
                selectedProduct == null
            ) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = {
                        selectedTab = it
                    }
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LuxuryWhite)
        ) {
            when {
                showShakeDiscover -> ShakeToDiscoverScreen(
                    products = sampleProducts,
                    onProductClick = {
                        selectedProduct = it
                        showShakeDiscover = false
                    },
                    onBack = {
                        showShakeDiscover = false
                    }
                )
                // MY OUTFITS
                showMyOutfits -> MyOutfitsScreen(
                    onBack = {
                        showMyOutfits = false
                    }
                )

                // GALAXY VISION
                showGalaxyVision -> GalaxyVisionScreen(
                    products = sampleProducts,
                    onProductClick = {
                        selectedProduct = it
                        showGalaxyVision = false
                    },
                    onBack = {
                        showGalaxyVision = false
                    }
                )

                // CHECKOUT
                showCheckout -> CheckoutScreen(
                    cartItems = cartItems,
                    onBack = {
                        showCheckout = false
                        showCart = true
                    }
                )

                // CART
                showCart -> CartScreen(
                    cartItems = cartItems,
                    onBack = {
                        showCart = false
                    },
                    onRemove = {
                        cartItems.remove(it)
                    },
                    onCheckout = {
                        if (cartItems.isNotEmpty()) {
                            showCart = false
                            showCheckout = true
                        }
                    }
                )

                // PRODUCT DETAILS
                selectedProduct != null -> {
                    ProductDetailScreen(
                        product = selectedProduct!!,
                        onBack = {
                            selectedProduct = null
                        },
                        onAddToCart = { product, size ->
                            val index = cartItems.indexOfFirst {
                                it.product.id == product.id &&
                                        it.size == size
                            }

                            if (index >= 0) {
                                val existing = cartItems[index]

                                cartItems[index] = existing.copy(
                                    quantity = existing.quantity + 1
                                )
                            } else {
                                cartItems.add(
                                    CartItem(product, size)
                                )
                            }

                            selectedProduct = null
                            showCheckout = false
                            showCart = true
                        }
                    )
                }

                // HOME
                selectedTab == 0 -> HomeScreen(
                    onCategoryClick = { category ->
                        if (category == "Galaxy Club") {
                            selectedTab = 3
                        } else {
                            selectedCategory =
                                if (category == "Femei") {
                                    "Femei"
                                } else {
                                    "Barbati"
                                }

                            selectedTab = 1
                        }
                    },
                    onProductClick = {
                        selectedProduct = it
                    },
                    favoriteIds = favoriteIds,
                    onFavoriteClick = {
                        toggleFavorite(it)
                    }
                )

                // SHOP
                selectedTab == 1 -> CatalogScreen(
                    category = selectedCategory,
                    onProductClick = {
                        selectedProduct = it
                    },
                    favoriteIds = favoriteIds,
                    onFavoriteClick = {
                        toggleFavorite(it)
                    }
                )

                // SAVED
                selectedTab == 2 -> FavoritesScreen(
                    products = sampleProducts.filter {
                        favoriteIds.contains(it.id)
                    },
                    onProductClick = {
                        selectedProduct = it
                    },
                    onFavoriteClick = {
                        toggleFavorite(it)
                    }
                )

                // GALAXY CLUB
                selectedTab == 3 -> GalaxyMatchScreen(
                    products = sampleProducts,
                    onProductClick = {
                        selectedProduct = it
                    },
                    onBack = {
                        selectedTab = 0
                    }
                )

                // YOU
                else -> Column {

                    Button(
                        onClick = {
                            showGalaxyVision = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LuxuryBlack,
                            contentColor = LuxuryWhite
                        )
                    ) {
                        Text("GALAXY VISION · STYLE WITH PHOTO")
                    }

                    Button(
                        onClick = {
                            showMyOutfits = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 4.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LuxuryBlack,
                            contentColor = LuxuryWhite
                        )
                    ) {
                        Text("MY OUTFITS · MANAGE YOUR LOOKS")
                    }

                    Button(
                        onClick = {
                            showShakeDiscover = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 4.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LuxuryBlack,
                            contentColor = LuxuryWhite
                        )
                    ) {
                        Text("SHAKE TO DISCOVER")
                    }

                    // LOGOUT
                    OutlinedButton(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 4.dp
                            )
                    ) {
                        Text("LOG OUT")
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        AccountScreen(
                            favoriteCount = favoriteIds.size,
                            onFavoritesClick = {
                                selectedTab = 2
                            }
                        )
                    }
                }
            }
        }
    }
}

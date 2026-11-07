package com.example.outletfashion.ui.screens

import android.net.Uri
import android.content.Context
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.saveable.rememberSaveable
import java.io.File
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

import com.example.outletfashion.data.model.Product
import com.example.outletfashion.model.ClothingAnalysis
import com.example.outletfashion.model.DemoClothingAnalyzer
import com.example.outletfashion.ui.theme.*

@Composable
fun GalaxyVisionScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var selectedImageString by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    val selectedImage = selectedImageString?.let(Uri::parse)

    var pendingCameraUriString by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var cameraError by remember { mutableStateOf<String?>(null) }

    fun createCameraUri(): Uri {
        val imageDirectory = File(context.cacheDir, "images")
        if (!imageDirectory.exists()) imageDirectory.mkdirs()
        val photoFile = File.createTempFile("galaxy_", ".jpg", imageDirectory)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    }

    var category by remember {
        mutableStateOf("Femei")
    }

    var clothingType by remember {
        mutableStateOf("Sacouri")
    }

    var selectedColor by remember {
        mutableStateOf("Bej")
    }

    var selectedStyle by remember {
        mutableStateOf("Minimalist")
    }

    var analysis by remember {
        mutableStateOf<ClothingAnalysis?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageString = uri.toString()
            analysis = null
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        val pendingUri = pendingCameraUriString
        if (success && pendingUri != null) {
            selectedImageString = pendingUri
            analysis = null
            cameraError = null
        } else if (!success) {
            cameraError = "Fotografia a fost anulata sau nu a putut fi salvata."
        }
        pendingCameraUriString = null
    }

    fun launchCamera() {
        try {
            val uri = createCameraUri()
            pendingCameraUriString = uri.toString()
            cameraError = null
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            pendingCameraUriString = null
            cameraError = "Nu s-a putut deschide camera. Verifica emulatorul."
        }
    }

    val clothingTypes = listOf(
        "Sacouri",
        "Rochii",
        "Jeans",
        "Camasi",
        "Pantaloni",
        "Pulovere",
        "Tricouri",
        "Jachete"
    )

    val colors = listOf(
        "Bej",
        "Negru",
        "Alb",
        "Albastru",
        "Gri",
        "Maro"
    )

    val styles = listOf(
        "Minimalist",
        "Elegant",
        "Casual"
    )

    val complementaryTypes = when (analysis?.clothingType) {
        "Sacouri" -> listOf(
            "Pantaloni", "Camasi", "Tricouri"
        )

        "Rochii" -> listOf(
            "Jachete", "Genti", "Paltoane"
        )

        "Jeans" -> listOf(
            "Tricouri", "Camasi", "Pulovere"
        )

        "Camasi" -> listOf(
            "Pantaloni", "Jeans", "Sacouri"
        )

        "Pantaloni" -> listOf(
            "Camasi", "Tricouri", "Pulovere"
        )

        "Pulovere" -> listOf(
            "Jeans", "Pantaloni", "Camasi"
        )

        "Tricouri" -> listOf(
            "Jeans", "Pantaloni", "Jachete"
        )

        else -> listOf(
            "Pantaloni", "Jeans", "Camasi"
        )
    }

    val recommendations = if (analysis == null) {
        emptyList()
    } else {
        products.filter { product ->
            product.category == category &&
                    complementaryTypes.any { type ->
                        product.type.equals(
                            type,
                            ignoreCase = true
                        )
                    }
        }.take(6)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryWhite),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {
            TextButton(onClick = onBack) {
                Text("← BACK", color = LuxuryBlack)
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "GALAXY VISION",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp,
                color = LuxuryBlack
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Upload a piece. Discover your look.",
                fontSize = 13.sp,
                color = LuxuryGray
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "DEMO ANALYSIS",
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                color = LuxuryGray
            )
        }

        item {
            VisionHeading("01 / UPLOAD YOUR ITEM")

            if (selectedImage == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(LuxuryLightGray)
                        .clickable { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("+", fontSize = 42.sp, color = LuxuryBlack)
                        Text(
                            "UPLOAD PHOTO",
                            fontSize = 12.sp,
                            letterSpacing = 1.5.sp,
                            color = LuxuryBlack
                        )
                    }
                }
            } else {
                AsyncImage(
                    model = selectedImage,
                    contentDescription = "Uploaded clothing",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(LuxuryLightGray)
                )
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { launchCamera() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryBlack,
                    contentColor = LuxuryWhite
                )
            ) {
                Text("TAKE PHOTO")
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = { imagePicker.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text("CHOOSE FROM GALLERY", color = LuxuryBlack)
            }

            cameraError?.let { message ->
                Spacer(Modifier.height(8.dp))
                Text(message, color = MaterialTheme.colorScheme.error)
            }
        }

        item {
            VisionHeading("02 / SELECT CATEGORY")

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {
                VisionOption(
                    title = "WOMAN",
                    selected = category == "Femei",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        category = "Femei"
                        analysis = null
                    }
                )

                VisionOption(
                    title = "MAN",
                    selected = category == "Barbati",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        category = "Barbati"
                        analysis = null
                    }
                )
            }
        }

        item {
            VisionHeading("03 / IDENTIFY THE ITEM")

            clothingTypes.chunked(2).forEach { rowTypes ->
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    rowTypes.forEach { type ->
                        VisionOption(
                            title = type.uppercase(),
                            selected = clothingType == type,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                clothingType = type
                                analysis = null
                            }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }

        item {
            VisionHeading("04 / ITEM COLOR")

            colors.chunked(3).forEach { rowColors ->
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    rowColors.forEach { color ->
                        VisionOption(
                            title = color.uppercase(),
                            selected = selectedColor == color,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                selectedColor = color
                                analysis = null
                            }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }

        item {
            VisionHeading("05 / STYLE")

            styles.forEach { style ->
                VisionOption(
                    title = style.uppercase(),
                    selected = selectedStyle == style,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedStyle = style
                        analysis = null
                    }
                )

                Spacer(Modifier.height(8.dp))
            }
        }

        item {
            Button(
                onClick = {
                    analysis = DemoClothingAnalyzer.analyze(
                        selectedType = clothingType,
                        selectedColor = selectedColor,
                        selectedStyle = selectedStyle
                    )
                },
                enabled = selectedImage != null,
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
                    text = "GENERATE MY LOOK",
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Demo: this result uses your manual " +
                        "selections. AI image recognition is " +
                        "not connected yet.",
                fontSize = 11.sp,
                color = LuxuryGray
            )
        }

        if (analysis != null) {

            item {
                HorizontalDivider(color = LuxuryBorder)

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "YOUR ITEM PROFILE",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(16.dp))

                VisionInfoRow(
                    label = "TYPE",
                    value = analysis!!.clothingType
                )

                VisionInfoRow(
                    label = "COLOR",
                    value = analysis!!.color
                )

                VisionInfoRow(
                    label = "STYLE",
                    value = analysis!!.style
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Demo result based on your selections.",
                    fontSize = 11.sp,
                    color = LuxuryGray
                )
            }

            item {
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "COMPLETE YOUR LOOK",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.5.sp,
                    color = LuxuryBlack
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Pieces selected from Outlet Galaxy.",
                    fontSize = 12.sp,
                    color = LuxuryGray
                )
            }

            if (recommendations.isEmpty()) {
                item {
                    Text(
                        text = "No matching products found.",
                        color = LuxuryGray
                    )
                }
            } else {
                items(recommendations) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onProductClick(product)
                            }
                            .padding(vertical = 8.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = product.imageUrl.toIntOrNull()
                                ?: product.imageUrl,
                            contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(90.dp)
                                .height(115.dp)
                                .background(LuxuryLightGray)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = product.brand.uppercase(),
                                fontSize = 10.sp,
                                color = LuxuryGray
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = product.name,
                                fontSize = 14.sp,
                                color = LuxuryBlack
                            )

                            Spacer(Modifier.height(7.dp))

                            Text(
                                text = "%.2f LEI".format(
                                    product.price
                                ),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = LuxuryBlack
                            )
                        }

                        Text(
                            text = "›",
                            fontSize = 25.sp,
                            color = LuxuryGray
                        )
                    }

                    HorizontalDivider(color = LuxuryBorder)
                }
            }
        }
    }
}

@Composable
private fun VisionHeading(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.2.sp,
        color = LuxuryBlack,
        modifier = Modifier.padding(bottom = 14.dp)
    )
}

@Composable
private fun VisionOption(
    title: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                if (selected) LuxuryBlack
                else LuxuryLightGray
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 8.dp,
                vertical = 15.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            color = if (selected) LuxuryWhite else LuxuryBlack
        )
    }
}

@Composable
private fun VisionInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = LuxuryGray
        )

        Text(
            text = value.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = LuxuryBlack
        )
    }

    HorizontalDivider(color = LuxuryBorder)
}



package com.example.outletfashion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LuxuryColors = lightColorScheme(
    primary = LuxuryBlack,
    onPrimary = LuxuryWhite,
    background = LuxuryWhite,
    onBackground = LuxuryBlack,
    surface = LuxuryWhite,
    onSurface = LuxuryBlack,
    surfaceVariant = LuxuryLightGray,
    onSurfaceVariant = LuxuryBlack,
    outline = LuxuryBorder
)

@Composable
fun OutletFashionTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LuxuryColors,
        content = content
    )
}

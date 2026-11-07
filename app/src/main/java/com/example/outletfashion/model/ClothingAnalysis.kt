package com.example.outletfashion.model

data class ClothingAnalysis(
    val clothingType: String,
    val color: String,
    val style: String,
    val isDemo: Boolean = true
)

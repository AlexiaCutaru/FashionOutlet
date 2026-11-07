package com.example.outletfashion.model

object DemoClothingAnalyzer {

    fun analyze(
        selectedType: String,
        selectedColor: String,
        selectedStyle: String
    ): ClothingAnalysis {

        return ClothingAnalysis(
            clothingType = selectedType,
            color = selectedColor,
            style = selectedStyle,
            isDemo = true
        )
    }
}
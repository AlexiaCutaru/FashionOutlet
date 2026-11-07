
package com.example.outletfashion.data.model
import com.example.outletfashion.R
data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val oldPrice: Double,
    val category: String,
    val type: String,
    val imageUrl: String,
    val sizes: List<String> = listOf("S", "M", "L", "XL")
) {
    val discount: Int
        get() = if (oldPrice > price && oldPrice > 0) {
            ((oldPrice - price) / oldPrice * 100).toInt()
        } else {
            0
        }
}

// MARIMI
private val clothingSizes = listOf("XS", "S", "M", "L", "XL")
private val trouserSizes = listOf("34", "36", "38", "40", "42")
private val mensTrouserSizes = listOf("30", "32", "34", "36", "38")
private val oneSize = listOf("ONE SIZE")

// FOTOGRAFII DEMONSTRATIVE SEPARATE
private fun demoImage(id: Int): String {

    val womenPhotos = listOf(
        "photo-1539109136881-3be0616acf4b",
        "photo-1483985988355-763728e1935b",
        "photo-1485968579580-b6d095142e6e",
        "photo-1490481651871-ab68de25d43d",
        "photo-1487222477894-8943e31ef7b2",
        "photo-1515886657613-9f3515b0c78f",
        "photo-1525507119028-ed4c629a60a3",
        "photo-1496747611176-843222e1e57c"
    )

    val menPhotos = listOf(
        "photo-1617127365659-c47fa864d8bc",
        "photo-1516257984-b1b4d707412e",
        "photo-1506794778202-cad84cf45f1d",
        "photo-1500648767791-00dcc994a43e",
        "photo-1519085360753-af0119f7cbe7"
    )

    val photo = if (id <= 30) {
        womenPhotos[(id - 1) % womenPhotos.size]
    } else {
        menPhotos[(id - 31) % menPhotos.size]
    }

    return "https://images.unsplash.com/$photo?w=800&q=85"
}

// FUNCTIE PENTRU CREAREA PRODUSELOR

private fun item(
    id: Int,
    name: String,
    brand: String,
    price: Double,
    oldPrice: Double,
    category: String,
    type: String,
    sizes: List<String> = clothingSizes,
    imageUrl: String? = null
): Product {
    return Product(
        id = id,
        name = name,
        brand = brand,
        price = price,
        oldPrice = oldPrice,
        category = category,
        type = type,
        imageUrl = imageUrl ?: demoImage(id),
        sizes = sizes
    )
}


// CATALOG COMPLET - 60 PRODUSE DEMO
val sampleProducts: List<Product> = listOf(

    // FEMEI - ZARA
    item(
        id = 1,
        name = "Sacou structurat bej",
        brand = "Zara",
        price = 249.90,
        oldPrice = 399.90,
        category = "Femei",
        type = "Sacouri",
        imageUrl = R.drawable.zara_sacou_bej.toString()
    ),

    item(2, "Rochie midi satinata", "Zara", 159.90, 259.90, "Femei", "Rochii"),
    item(3, "Jeans wide leg", "Zara", 139.90, 229.90, "Femei", "Blugi", trouserSizes),
    item(4, "Camasa alba oversized", "Zara", 119.90, 199.90, "Femei", "Camasi"),
    item(5, "Palton lung din amestec de lana", "Zara", 349.90, 599.90, "Femei", "Paltoane"),

    // FEMEI - MANGO
    item(6, "Trench clasic camel", "Mango", 299.90, 499.90, "Femei", "Jachete"),
    item(7, "Rochie eleganta neagra", "Mango", 189.90, 329.90, "Femei", "Rochii"),
    item(8, "Pantaloni eleganti drepti", "Mango", 149.90, 249.90, "Femei", "Pantaloni", trouserSizes),
    item(9, "Pulover fin cu guler rotund", "Mango", 129.90, 219.90, "Femei", "Pulovere"),
    item(10, "Geanta shopper minimalista", "Mango", 169.90, 299.90, "Femei", "Genti", oneSize),

    // FEMEI - MASSIMO DUTTI
    item(11, "Sacou din in natural", "Massimo Dutti", 399.90, 699.90, "Femei", "Sacouri"),
    item(12, "Camasa din bumbac premium", "Massimo Dutti", 199.90, 349.90, "Femei", "Camasi"),
    item(13, "Pantaloni tailored", "Massimo Dutti", 259.90, 449.90, "Femei", "Pantaloni", trouserSizes),
    item(14, "Pulover din amestec de casmir", "Massimo Dutti", 329.90, 549.90, "Femei", "Pulovere"),
    item(15, "Fusta midi plisata", "Massimo Dutti", 219.90, 369.90, "Femei", "Fuste", trouserSizes),

    // FEMEI - OYSHO
    item(16, "Leggings sport high waist", "Oysho", 99.90, 179.90, "Femei", "Sport"),
    item(17, "Top sport seamless", "Oysho", 79.90, 139.90, "Femei", "Sport"),
    item(18, "Hanorac relaxed fit", "Oysho", 149.90, 249.90, "Femei", "Hanorace"),
    item(19, "Jacheta sport usoara", "Oysho", 199.90, 329.90, "Femei", "Sport"),
    item(20, "Pantaloni jogger", "Oysho", 119.90, 199.90, "Femei", "Sport"),

    // FEMEI - BERSHKA
    item(21, "Jeans baggy", "Bershka", 109.90, 199.90, "Femei", "Blugi", trouserSizes),
    item(22, "Geaca denim cropped", "Bershka", 159.90, 279.90, "Femei", "Jachete"),
    item(23, "Top ribbed", "Bershka", 49.90, 89.90, "Femei", "Topuri"),
    item(24, "Fusta mini denim", "Bershka", 89.90, 159.90, "Femei", "Fuste", trouserSizes),
    item(25, "Rochie casual", "Bershka", 99.90, 179.90, "Femei", "Rochii"),

    // FEMEI - STRADIVARIUS SI COS
    item(26, "Camasa cu dungi fine", "Stradivarius", 109.90, 189.90, "Femei", "Camasi"),
    item(27, "Pantaloni wide leg", "Stradivarius", 129.90, 219.90, "Femei", "Pantaloni", trouserSizes),
    item(28, "Cardigan tricotat", "Stradivarius", 139.90, 229.90, "Femei", "Pulovere"),
    item(29, "Rochie minimalista", "COS", 289.90, 479.90, "Femei", "Rochii"),
    item(30, "Camasa oversized premium", "COS", 249.90, 419.90, "Femei", "Camasi"),

    // BARBATI - ZARA
    item(31, "Sacou slim fit", "Zara", 299.90, 499.90, "Barbati", "Sacouri"),
    item(32, "Camasa Oxford alba", "Zara", 129.90, 219.90, "Barbati", "Camasi"),
    item(33, "Jeans straight fit", "Zara", 149.90, 249.90, "Barbati", "Blugi", mensTrouserSizes),
    item(34, "Tricou basic premium", "Zara", 59.90, 99.90, "Barbati", "Tricouri"),
    item(35, "Geaca bomber", "Zara", 229.90, 399.90, "Barbati", "Jachete"),

    // BARBATI - MANGO
    item(36, "Palton elegant camel", "Mango", 399.90, 699.90, "Barbati", "Paltoane"),
    item(37, "Pantaloni chino", "Mango", 169.90, 279.90, "Barbati", "Pantaloni", mensTrouserSizes),
    item(38, "Pulover cu guler inalt", "Mango", 159.90, 269.90, "Barbati", "Pulovere"),
    item(39, "Camasa casual din in", "Mango", 149.90, 249.90, "Barbati", "Camasi"),
    item(40, "Tricou polo", "Mango", 109.90, 189.90, "Barbati", "Tricouri"),

    // BARBATI - MASSIMO DUTTI
    item(41, "Sacou din lana", "Massimo Dutti", 499.90, 899.90, "Barbati", "Sacouri"),
    item(42, "Camasa premium albastra", "Massimo Dutti", 229.90, 399.90, "Barbati", "Camasi"),
    item(43, "Pantaloni eleganti", "Massimo Dutti", 299.90, 499.90, "Barbati", "Pantaloni", mensTrouserSizes),
    item(44, "Pulover din bumbac", "Massimo Dutti", 249.90, 419.90, "Barbati", "Pulovere"),
    item(45, "Jacheta casual premium", "Massimo Dutti", 449.90, 749.90, "Barbati", "Jachete"),

    // BARBATI - PULL&BEAR
    item(46, "Jeans relaxed fit", "Pull&Bear", 119.90, 199.90, "Barbati", "Blugi", mensTrouserSizes),
    item(47, "Hanorac oversized", "Pull&Bear", 129.90, 219.90, "Barbati", "Hanorace"),
    item(48, "Tricou relaxed", "Pull&Bear", 69.90, 119.90, "Barbati", "Tricouri"),
    item(49, "Geaca denim", "Pull&Bear", 189.90, 319.90, "Barbati", "Jachete"),
    item(50, "Pantaloni cargo", "Pull&Bear", 149.90, 249.90, "Barbati", "Pantaloni", mensTrouserSizes),

    // BARBATI - BERSHKA
    item(51, "Jeans loose fit", "Bershka", 119.90, 209.90, "Barbati", "Blugi", mensTrouserSizes),
    item(52, "Hanorac cu fermoar", "Bershka", 139.90, 229.90, "Barbati", "Hanorace"),
    item(53, "Tricou boxy fit", "Bershka", 59.90, 109.90, "Barbati", "Tricouri"),
    item(54, "Jacheta varsity", "Bershka", 229.90, 379.90, "Barbati", "Jachete"),
    item(55, "Pantaloni jogger", "Bershka", 109.90, 189.90, "Barbati", "Pantaloni", mensTrouserSizes),

    // BARBATI - COS
    item(56, "Camasa minimalista", "COS", 269.90, 449.90, "Barbati", "Camasi"),
    item(57, "Pantaloni relaxed tailored", "COS", 319.90, 529.90, "Barbati", "Pantaloni", mensTrouserSizes),
    item(58, "Pulover minimalist", "COS", 289.90, 479.90, "Barbati", "Pulovere"),
    item(59, "Tricou din bumbac gros", "COS", 129.90, 219.90, "Barbati", "Tricouri"),
    item(60, "Jacheta structurata", "COS", 449.90, 749.90, "Barbati", "Jachete")
)

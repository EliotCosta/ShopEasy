package com.ec.shopeasy.data

import com.google.gson.annotations.SerializedName

data class ProductInfos (
        @SerializedName("id")
    var id: String,

        @SerializedName("product_name")
    var productName: String,

        @SerializedName("product_name_fr")
    var productNameFr: String,

        @SerializedName("categories")
    var categories: String,

        @SerializedName("image_url")
    var imageUrl: String,

        @SerializedName("nutriscore_data")
    var nutriScoreData: NutriScoreData,

        @SerializedName("nutriments")
    var nutriments: Nutriments
)

data class NutriScoreData (

        @SerializedName("energy")
        var energy: Float,

        @SerializedName("fiber")
        var fiber: Float,

        @SerializedName("proteins")
        var proteins: Float,

        @SerializedName("saturated_fat")
        var saturatedFat: Float,

        @SerializedName("saturated_fat_ratio")
        var saturatedFatRatio: Float,

        @SerializedName("sodium")
        var sodium: Float,

        @SerializedName("sugars")
        var sugars: Float,

        @SerializedName("score")
        var score: Float,

        @SerializedName("grade")
        var grade: String
)

data class Nutriments (

        @SerializedName("carbohydrates")
        var carbohydrates: Float,
        @SerializedName("carbohydrates_units")
        var carbohydratesUnits: String,

        @SerializedName("energy-kcal")
        var energy: Float,
        @SerializedName("energy-kcal_unit")
        var energyUnit: String,

        @SerializedName("fat")
        var fat: Float,
        @SerializedName("fat_unit")
        var fateUnit: String,

        @SerializedName("fiber")
        var fiber: Float,

        @SerializedName("proteins")
        var proteins: Float,
        @SerializedName("proteins_unit")
        var proteinsUnit: String,

        @SerializedName("salt")
        var salt: Float,
        @SerializedName("salt_unit")
        var saltUnit: String,

        @SerializedName("saturated-fat")
        var saturatedFat: Float,
        @SerializedName("saturated-fat_unit")
        var saturatedFatUnit: String,

        @SerializedName("sodium")
        var sodium: Float,
        @SerializedName("sodium_unit")
        var sodiumUnit: String,

        @SerializedName("sugars")
        var sugars: Float,
        @SerializedName("sugars_unit")
        var sugarsUnit: String
)
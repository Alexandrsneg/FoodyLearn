package com.example.domain.models


data class ExtendedIngredientRest(
    val amount: Double? = 0.0,
    val consistency: String?,
    val image: String?,
    val name: String?,
    val original: String?,
    val unit: String?
)

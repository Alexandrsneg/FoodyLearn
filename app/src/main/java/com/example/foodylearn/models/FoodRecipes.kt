package com.example.foodylearn.models


import com.google.gson.annotations.SerializedName

data class FoodRecipes(
    @SerializedName("results")
    val results: List<Result>
)
package com.example.foodylearn.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Double? = 0.0,
    @SerializedName("consistency")
    val consistency: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original")
    val original: String?,
    @SerializedName("unit")
    val unit: String?
) : Parcelable
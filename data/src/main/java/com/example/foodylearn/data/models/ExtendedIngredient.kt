package com.example.foodylearn.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    val amount: Double? = 0.0,
    val consistency: String?,
    val image: String?,
    val name: String?,
    val original: String?,
    val unit: String?
) : Parcelable
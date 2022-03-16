package com.elmorshdi.trainingtask.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("image")
    val image: String?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("price")
    val price: Int? =null,
    @SerializedName("quantity")
    val quantity: Int? =null,
    @SerializedName("restaurant_id")
    val restaurant_id: Int?=null
): Parcelable
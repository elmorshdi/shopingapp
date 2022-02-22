package com.elmorshdi.trainingtask.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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
    val quantity: Int?=null,
    @SerializedName("restaurant_id")
    val restaurant_id: Int?=null
): Parcelable {

}
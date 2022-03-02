package com.elmorshdi.trainingtask.view.util

import android.graphics.Color
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.helper.toast
import com.elmorshdi.trainingtask.view.adapter.GridProductAdapter
import com.elmorshdi.trainingtask.view.adapter.HorizontalProductAdapter
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("loadImageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    if (!url.isNullOrBlank()) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}
@BindingAdapter("ProductsList")
fun bindProductsList(recyclerView: RecyclerView, list: List<Product>?) {

    list?.let { (recyclerView.adapter as GridProductAdapter).submitList(list) }

}
@BindingAdapter("ProductsListHor")
fun bindProductsListHor(recyclerView: RecyclerView, list: List<Product>?) {

    list?.let { (recyclerView.adapter as HorizontalProductAdapter).submitList(list) }

}
//@BindingAdapter("onDeleteClick")
//fun onClick(button: AppCompatButton, onClick: (View,Int) -> Unit,int: Int) {
//    button.setOnClickListener {
//        onClick(it, int)
//    }}
@BindingAdapter("showToast")
fun showToast(view: View, msg: String?) {
    if (!msg.isNullOrBlank()) {
        view.context.toast(msg)

    }}

@BindingAdapter("setQuantity")
fun setQuantity(textView: AppCompatTextView, quantity: String?) {
textView.text="Quantity $quantity"
}


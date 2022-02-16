package com.elmorshdi.trainingtask;

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elmorshdi.trainingtask.model.Product


class ProductAdapter(private val list: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item: Product = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

}

class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {
   private var name: AppCompatTextView? = null
    private var price: AppCompatTextView? = null
    private var image: ImageView? = null


    init {
        name = itemView.findViewById(R.id.item_name)
        price = itemView.findViewById(R.id.item_price)
        image = itemView.findViewById(R.id.item_image)
    }

    fun bind(item: Product) {
        Glide.with(image!!.context)
            .load(item.image)
            .error(R.drawable.ic_launcher_background)
            .into(image!!)
        name?.text = item.name
       price?.text = item.price.toString()
    }

}
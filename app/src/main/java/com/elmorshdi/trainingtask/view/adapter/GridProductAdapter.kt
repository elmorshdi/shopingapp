package com.elmorshdi.trainingtask.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.trainingtask.databinding.ItemBinding
import com.elmorshdi.trainingtask.domain.model.Product


class GridProductAdapter(
    private val interaction: Interaction? = null
    ): ListAdapter<Product, GridProductAdapter.GridProductViewHolder>(ProductDiffCallback()) {

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

       class GridProductViewHolder constructor(
        private val binding: ItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

           fun bind(product: Product ) = with(itemView) {
            binding.product = product
            binding.executePendingBindings()

            //Notify the listener on item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=ItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return GridProductViewHolder(
            binding , interaction
        )
    }


    override fun onBindViewHolder(holder: GridProductAdapter.GridProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }









    interface Interaction {
        fun onItemSelected(product: Product)

    }


}



package com.bangkit.martq.paging.orderReview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.martq.data.model.ProductOrder
import com.bangkit.martq.databinding.ItemProductOrderBinding

class ListOrderReviewAdapter: ListAdapter<ProductOrder, ListOrderReviewAdapter.MyViewHolder>(ListOrderReviewAdapter.DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemProductOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductOrder){
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = product.price.toString()
            binding.tvQuantity.text = product.quantity.toString()
            binding.tvPrice.text = product.subTotal.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOrderReviewAdapter.MyViewHolder {
        val binding = ItemProductOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListOrderReviewAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListOrderReviewAdapter.MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductOrder>() {
            override fun areItemsTheSame(oldItem: ProductOrder, newItem: ProductOrder): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ProductOrder, newItem: ProductOrder): Boolean {
                return oldItem == newItem
            }
        }
    }
}
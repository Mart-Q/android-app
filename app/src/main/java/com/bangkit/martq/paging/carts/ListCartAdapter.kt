package com.bangkit.martq.paging.carts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.martq.R
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.databinding.ItemCartProductBinding
import com.bumptech.glide.Glide

class ListCartAdapter: ListAdapter<Cart, ListCartAdapter.MyViewHolder>(ListCartAdapter.DIFF_CALLBACK) {

    private lateinit var onItemClickCallback : ListCartAdapter.OnItemClickCallback

    class MyViewHolder(val binding: ItemCartProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart){
            Glide.with(itemView.context)
                .load(cart.image)
                .placeholder(R.drawable.baseline_broken_image_24)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.ivProductImage)
            binding.tvProductName.text = cart.productName
            binding.tvProductPrice.text = cart.price.toString()
            binding.tvQuantity.text = cart.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCartAdapter.MyViewHolder {
        val binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListCartAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListCartAdapter.MyViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
        holder.binding.btnAdd.setOnClickListener{ onItemClickCallback.onBtnAddClicked(cart) }
        holder.binding.btnSubtract.setOnClickListener{ onItemClickCallback.onBtnSubtractClicked(cart) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onBtnAddClicked(cart: Cart)
        fun onBtnSubtractClicked(cart: Cart)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }
}
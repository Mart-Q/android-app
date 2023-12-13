package com.bangkit.martq.paging.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.martq.data.remote.response.CategoryItem
import com.bangkit.martq.databinding.ItemProductCategoryBinding

class ListCategoryAdapter : ListAdapter<CategoryItem, ListCategoryAdapter.MyViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback : OnItemClickCallback

    class MyViewHolder(val binding: ItemProductCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryItem){
            binding.tvProductCategory.text = category.namaKategori
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(category) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(category: CategoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryItem>() {
            override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
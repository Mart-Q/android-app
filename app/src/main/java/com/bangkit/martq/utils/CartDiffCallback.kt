package com.bangkit.martq.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.martq.data.local.room.Cart

class CartDiffCallback(private val oldCartList:List<Cart>, private val newCartList: List<Cart>) : DiffUtil.Callback(){
    override fun getOldListSize():Int=oldCartList.size
    override fun getNewListSize():Int=newCartList.size
    override fun areItemsTheSame(oldItemPosition:Int,newItemPosition:Int):Boolean{
        return oldCartList[oldItemPosition].id==newCartList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition:Int,newItemPosition:Int):Boolean{
        val oldCart=oldCartList[oldItemPosition]
        val newCart=newCartList[newItemPosition]
        return oldCart.id==newCart.id&&oldCart.productName==newCart.productName
    }

}
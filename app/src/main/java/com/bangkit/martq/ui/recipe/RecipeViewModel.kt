package com.bangkit.martq.ui.recipe

import androidx.lifecycle.ViewModel
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.repository.CartRepository

class RecipeViewModel(private val cartRepo: CartRepository) : ViewModel() {

    fun addToCart(cart: Cart) {
        cartRepo.insert(cart)
    }
}
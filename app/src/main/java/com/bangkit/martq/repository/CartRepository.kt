package com.bangkit.martq.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.data.local.room.CartDao
import com.bangkit.martq.data.local.room.CartRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartRepository(application: Application) {
    private val mCartDao: CartDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = CartRoomDatabase.getDatabase(application)
        mCartDao = db.cartDao()
    }

    fun getAllCartItems(): LiveData<List<Cart>> = mCartDao.getCartItems()

    fun insert(cart: Cart) {
        executorService.execute { mCartDao.insert(cart) }
    }

    fun delete(cart: Cart) {
        executorService.execute { mCartDao.delete(cart) }
    }

    fun update(cart: Cart) {
        executorService.execute { mCartDao.update(cart) }
    }
}
package com.bangkit.martq.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cart: Cart)

    @Query("UPDATE cart SET quantity = quantity + :quantityToAdd WHERE productName = :productName")
    fun updateQuantity(productName: String, quantityToAdd: Int)

    @Query("SELECT * FROM cart WHERE productName = :productName LIMIT 1")
    fun getCartItemByName(productName: String): Cart?

    @Update
    fun update(cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Query("SELECT * from cart ORDER BY id ASC")
    fun getCartItems(): LiveData<List<Cart>>
}
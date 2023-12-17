package com.bangkit.martq.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class CartRoomDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CartRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): CartRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CartRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CartRoomDatabase::class.java, "cart_database")
                        .build()
                }
            }
            return INSTANCE as CartRoomDatabase
        }
    }
}
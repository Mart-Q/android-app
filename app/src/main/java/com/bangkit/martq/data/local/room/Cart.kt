package com.bangkit.martq.data.local.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize



@Entity(tableName = "Cart")
@Parcelize
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "productName")
    var productName: String = "",

    @ColumnInfo(name = "image")
    var image: String? = "",

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "price")
    var price: Int = 0,
) : Parcelable
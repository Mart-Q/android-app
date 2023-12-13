package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllProductsResponse(

	@field:SerializedName("produk")
	val produk: List<ProductItem> = emptyList()
) : Parcelable

@Parcelize
data class ProductItem(

	@field:SerializedName("Kategori")
	val kategori: String? = null,

	@field:SerializedName("Nama_Produk")
	val namaProduk: String? = null,

	@field:SerializedName("id_produk")
	val idProduk: Int? = null,

	@field:SerializedName("Image_URL")
	val imageURL: String? = null,

	@field:SerializedName("Harga")
	val harga: Int? = null,

	@field:SerializedName("Stock")
	val stock: Int? = null
) : Parcelable

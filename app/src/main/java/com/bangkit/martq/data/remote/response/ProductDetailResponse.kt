package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailResponse(

	@field:SerializedName("produk")
	val produk: Produk? = null
) : Parcelable

@Parcelize
data class Produk(

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

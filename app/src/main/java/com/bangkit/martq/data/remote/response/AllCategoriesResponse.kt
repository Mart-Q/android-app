package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllCategoriesResponse(

	@field:SerializedName("kategori")
	val kategori: List<CategoryItem>
) : Parcelable

@Parcelize
data class CategoryItem(

	@field:SerializedName("id_kategori")
	val idKategori: Int,

	@field:SerializedName("nama_kategori")
	val namaKategori: String
) : Parcelable

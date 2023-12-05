package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllCategoriesResponse(

	@field:SerializedName("data")
	val data: List<CategoryItem>,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class CategoryItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

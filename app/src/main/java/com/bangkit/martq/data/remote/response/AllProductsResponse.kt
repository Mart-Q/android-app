package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllProductsResponse(

	@field:SerializedName("data")
	val data: List<ProductItem> = emptyList(),

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ImagesItem(

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class ProductItem(

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: Category? = null
) : Parcelable

@Parcelize
data class Meta(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("page_size")
	val pageSize: Int? = null
) : Parcelable

@Parcelize
data class Category(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

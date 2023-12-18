package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostOrderResponse(

	@field:SerializedName("error")
	val error: String? = null
) : Parcelable

package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodInspirationResponse(

	@field:SerializedName("recommendations")
	val recommendations: List<String?>? = null
) : Parcelable

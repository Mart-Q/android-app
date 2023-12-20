package com.bangkit.martq.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllOrdersResponse(

	@field:SerializedName("pesanan")
	val pesanan: List<PesananItem?>? = null
) : Parcelable

@Parcelize
data class ProdukItem(

	@field:SerializedName("id_produk")
	val idProduk: Int? = null
) : Parcelable

@Parcelize
data class PesananItem(

	@field:SerializedName("Total Harga")
	val totalHarga: Int? = null,

	@field:SerializedName("Biaya Kirim")
	val biayaKirim: Int? = null,

	@field:SerializedName("id_rekening")
	val idRekening: Int? = null,

	@field:SerializedName("Produk")
	val produk: List<ProdukItem?>? = null,

	@field:SerializedName("id_market")
	val idMarket: String? = null,

	@field:SerializedName("id_pesanan")
	val idPesanan: Int? = null,

	@field:SerializedName("Waktu")
	val waktu: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("is_delivery")
	val isDelivery: String? = null
) : Parcelable

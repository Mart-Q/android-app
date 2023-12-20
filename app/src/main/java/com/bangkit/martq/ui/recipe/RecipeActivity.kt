package com.bangkit.martq.ui.recipe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.R
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.databinding.ActivityRecipeBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.carts.ListCartAdapter


class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    val viewModel by viewModels<RecipeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setBackgroundColor(resources.getColor(R.color.light_blue))

        val recipe = intent.getStringExtra(EXTRA_RECIPE)
        lateinit var products: List<Cart>

        if (recipe == "Gado Gado") {
            products = listOf(
                Cart(
                    id = 6,
                    productName = "Sayur Kol 500 gram",
                    price = 9000,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/kol.png",
                    quantity = 1,
                ),
                Cart(
                    id = 5,
                    productName = "Kerupuk Bundar",
                    price = 1000,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/kerupuk.png",
                    quantity = 1,
                ),
                Cart(
                    id = 9,
                    productName = "Sambal Kacang 250 gram",
                    price = 32000,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/sambal%20kacang.jpg",
                    quantity = 1,
                ),
                Cart(
                    id = 11,
                    productName = "Telur 1 butir",
                    price = 2000,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/telur%20perbijii.jpg",
                    quantity = 1,
                )
            )
        } else {
            products = listOf(
                Cart(
                    id = 6,
                    productName = "Ikan Gurame",
                    price = 21000,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/gurame.jpg",
                    quantity = 1,
                ),
                Cart(
                    id = 8,
                    productName = "Minyak Goreng 1 L",
                    price = 18800,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/minyak%20goreng.png",
                    quantity = 1,
                ),
                Cart(
                    id = 10,
                    productName = "Saus Tiram 1 Renceng",
                    price = 23700,
                    image = "https://storage.googleapis.com/storage-gambar-menu/Makanan/saus%20tiram.png",
                    quantity = 1,
                )
            )
        }

        setupAction(products)
        setUpCartList(products)
    }

    private fun setupAction(products: List<Cart>) {
        binding.btnAddToCart.setOnClickListener {

            for (product in products) {
                viewModel.addToCart(product)
            }

            Toast.makeText(this, "Berhasil menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setUpCartList(products: List<Cart>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvProductList.layoutManager = layoutManager

        setProducts(products)
    }

    private fun setProducts(products: List<Cart>) {
        val adapter = ListCartAdapter()
        adapter.submitList(products)
        binding.rvProductList.adapter = adapter

        adapter.setOnItemClickCallback(object : ListCartAdapter.OnItemClickCallback {
            override fun onBtnAddClicked(cart: Cart) {
            }

            override fun onBtnSubtractClicked(cart: Cart) {
            }
        })
    }

    companion object {
        private val EXTRA_RECIPE = "extra_recipe"
    }
}
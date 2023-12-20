package com.bangkit.martq.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.R
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.databinding.ActivityProductDetailBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.utils.ResultState
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    val viewModel by viewModels<ProductDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val productId = intent.getIntExtra("extra_id", 0)

        setupView(productId)
    }

    private fun setupView(productId: Int) {

        var mToastAddToCart: Toast? = null
        var productImage: String? = null

        viewModel.getProduct(productId).observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE

                    Glide.with(this)
                        .load(resultState.data.produk?.imageURL)
                        .placeholder(R.drawable.baseline_broken_image_24)
                        .error(R.drawable.baseline_broken_image_24)
                        .into(binding.ivProduct)

                    with(binding) {
                        tvProductName.text = resultState.data.produk?.namaProduk
                        tvPrice.text = resultState.data.produk?.harga.toString()
                    }

                    productImage = resultState.data.produk?.imageURL.toString()

                    binding.btnAddToCart.isEnabled = true
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                    binding.btnAddToCart.isEnabled = false
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnAddToCart.isEnabled = false

                    Toast.makeText(this, resultState.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            mToastAddToCart?.cancel()
            finish()
        }

        binding.btnSubtract.setOnClickListener {
            val quantity = binding.tvQuantity.text.toString().toInt()
            if (quantity > 1) {
                binding.tvQuantity.text = (quantity - 1).toString()
            }
        }

        binding.btnAdd.setOnClickListener {
            val quantity = binding.tvQuantity.text.toString().toInt()
            binding.tvQuantity.text = (quantity + 1).toString()
        }

        binding.btnAddToCart.setOnClickListener {
            val productName = binding.tvProductName.text.toString()
            val productPrice = binding.tvPrice.text.toString()
            val productQuantity = binding.tvQuantity.text.toString()

            val product = Cart(
                productName = productName,
                price = productPrice.toInt(),
                image = productImage ?: "",
                quantity = productQuantity.toInt(),
            )

            viewModel.addToCart(product)

            mToastAddToCart = Toast.makeText(this, "Berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT)
            mToastAddToCart?.show()
        }
    }
}
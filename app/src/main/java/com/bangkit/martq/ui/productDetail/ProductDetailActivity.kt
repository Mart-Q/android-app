package com.bangkit.martq.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.R
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.databinding.ActivityProductDetailBinding
import com.bangkit.martq.factory.ViewModelFactory
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

        viewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.getProduct(productId)

        viewModel.product.observe(this) {

            Glide.with(this)
                .load(it.produk?.imageURL)
                .placeholder(R.drawable.baseline_broken_image_24)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.ivProduct)

            with(binding) {
                tvProductName.text = it.produk?.namaProduk
                tvPrice.text = it.produk?.harga.toString()
            }
        }

        binding.btnBack.setOnClickListener {
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
            val productImage = viewModel.product.value?.produk?.imageURL.toString()
            val productQuantity = binding.tvQuantity.text.toString()

            val product = Cart(
                productName = productName,
                price = productPrice.toInt(),
                image = productImage,
                quantity = productQuantity.toInt(),
            )

            viewModel.addToCart(product)

            Toast.makeText(this, "$productName telah ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.bangkit.martq.ui.productDetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.R
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
    }
}
package com.bangkit.martq.ui.productPage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.data.remote.response.ProductItem
import com.bangkit.martq.databinding.ActivityProductsBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.products.ListProductAdapter
import com.bangkit.martq.ui.home.HomeFragment
import com.bangkit.martq.ui.productDetail.ProductDetailActivity

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    val viewModel by viewModels<ProductsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupData()
        setUpRecycler()
        updateList()
    }

    private fun setupData() {
        val category = intent.getStringExtra(EXTRA_CATEGORY_NAME)

        viewModel.getProductsByCategory(category.toString())
    }

    private fun setUpRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun updateList() {
        viewModel.products.observe(this) { product ->
            setProducts(product.produk)
        }
    }

    private fun setProducts(products: List<ProductItem>) {

        val adapter = ListProductAdapter()
        adapter.submitList(products)
        binding.rvProducts.adapter = adapter

        adapter.setOnItemClickCallback(object : ListProductAdapter.OnItemClickCallback {
            override fun onItemClicked(product: ProductItem) {
                val intentToDetail = Intent(applicationContext, ProductDetailActivity::class.java)
                val id = product.idProduk
                intentToDetail.putExtra(HomeFragment.EXTRA_ID, id)
                startActivity(intentToDetail)
            }
        })
    }

    companion object {
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }
}
package com.bangkit.martq.ui.productPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.martq.data.remote.response.ProductItem
import com.bangkit.martq.databinding.ActivityProductsBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.products.ListProductAdapter
import com.bangkit.martq.ui.home.HomeFragment
import com.bangkit.martq.ui.productDetail.ProductDetailActivity
import com.bangkit.martq.utils.ResultState

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
    }

    private fun setupData() {
        val category = intent.getStringExtra(EXTRA_CATEGORY_NAME)

        viewModel.getProductsByCategory(category.toString()).observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    setProducts(resultState.data.produk)
                    binding.progressBar.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpRecycler() {
        val layoutManager = GridLayoutManager(applicationContext, 2)
        binding.rvProducts.layoutManager = layoutManager
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
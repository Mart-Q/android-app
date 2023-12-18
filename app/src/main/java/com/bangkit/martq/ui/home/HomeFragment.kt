package com.bangkit.martq.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.data.remote.response.CategoryItem
import com.bangkit.martq.data.remote.response.ProductItem
import com.bangkit.martq.databinding.FragmentHomeBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.categories.ListCategoryAdapter
import com.bangkit.martq.paging.products.ListProductAdapter
import com.bangkit.martq.ui.productDetail.ProductDetailActivity
import com.bangkit.martq.ui.productPage.ProductsActivity
import com.bangkit.martq.utils.ResultState


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()
        setUpRecycler()
        updateList()

        return root
    }

    private fun setupView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()

                    // TODO: Implement search feature

                    false
                }

            btnRecipeRecom.setOnClickListener(View.OnClickListener {

            })

            binding.cardDailyRecipe.setOnClickListener(View.OnClickListener {
                onDailyRecipeClick(it)
            })
        }
    }

    private fun setUpRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvProductPopular.layoutManager = layoutManager

        val layoutManager2 = GridLayoutManager(requireContext(), 4)
        binding.rvCategory.layoutManager = layoutManager2

        val layoutManager3 = GridLayoutManager(requireContext(), 2)
        binding.rvProductsHomepage.layoutManager = layoutManager3
        binding.rvProductsHomepage.isNestedScrollingEnabled = false
    }

    private fun updateList() {
        viewModel.getProducts().observe(requireActivity()) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    setProducts(resultState.data.produk)
                    binding.pbProductPopular.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.pbProductPopular.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.pbProductPopular.visibility = View.GONE
                }
            }
        }

        viewModel.getCategories().observe(requireActivity()) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    setCategories(resultState.data.kategori)
                    binding.pbCategories.visibility = View.GONE
                }
                is ResultState.Loading -> {
                    binding.pbCategories.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.pbCategories.visibility = View.GONE
                }
            }
        }
    }

    private fun setProducts(products: List<ProductItem>) {

        val adapter = ListProductAdapter()
        adapter.submitList(products)
        binding.rvProductPopular.adapter = adapter
        binding.rvProductsHomepage.adapter = adapter

        adapter.setOnItemClickCallback(object : ListProductAdapter.OnItemClickCallback {
            override fun onItemClicked(product: ProductItem) {
                val intentToDetail = Intent(requireContext(), ProductDetailActivity::class.java)
                val id = product.idProduk
                intentToDetail.putExtra(EXTRA_ID, id)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setCategories(categories: List<CategoryItem>) {

        val adapter = ListCategoryAdapter()
        adapter.submitList(categories)
        binding.rvCategory.adapter = adapter

        adapter.setOnItemClickCallback(object : ListCategoryAdapter.OnItemClickCallback {
            override fun onItemClicked(category: CategoryItem) {
                val intentToProductsPage = Intent(requireContext(), ProductsActivity::class.java)
                val id = category.namaKategori
                intentToProductsPage.putExtra(HomeFragment.EXTRA_CATEGORY_NAME, id)

                startActivity(intentToProductsPage)
            }
        })
    }

    fun onDailyRecipeClick(view: View?) {

        val popupView = LayoutInflater.from(activity).inflate(com.bangkit.martq.R.layout.activity_recipe, null);
        val popupWindow = PopupWindow(
            popupView,
            1250,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        popupWindow.elevation = 20f
        popupWindow.isFocusable = true
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    companion object {
        private const val TAG = "HomeFragment"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }
}
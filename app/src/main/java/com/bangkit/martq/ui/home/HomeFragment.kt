package com.bangkit.martq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        }
    }

    private fun setUpRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvProductPopular.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(requireContext())
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCategory.layoutManager = layoutManager2

        val layoutManager3 = GridLayoutManager(requireContext(), 2)
        binding.rvProductsHomepage.layoutManager = layoutManager3
    }

    private fun updateList() {
        viewModel.products.observe(requireActivity()) { product ->
            setProducts(product.produk)
        }

        viewModel.category.observe(requireActivity()) { category ->
            setCategories(category.kategori)
        }
    }

    private fun setProducts(products: List<ProductItem>) {

        val adapter = ListProductAdapter()
        adapter.submitList(products)
        binding.rvProductPopular.adapter = adapter
        binding.rvProductsHomepage.adapter = adapter

        adapter.setOnItemClickCallback(object : ListProductAdapter.OnItemClickCallback {
            override fun onItemClicked(product: ProductItem) {
                // TODO: Implement detail product
//                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
//                intentToDetail.putExtra(EXTRA_STORY, story)
//                startActivity(intentToDetail)
            }
        })
    }

    private fun setCategories(categories: List<CategoryItem>) {

        val adapter = ListCategoryAdapter()
        adapter.submitList(categories)
        binding.rvCategory.adapter = adapter

        adapter.setOnItemClickCallback(object : ListCategoryAdapter.OnItemClickCallback {
            override fun onItemClicked(category: CategoryItem) {
            }
        })
    }

}
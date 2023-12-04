package com.bangkit.martq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.data.remote.response.ProductItem
import com.bangkit.martq.databinding.FragmentHomeBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.products.ListProductAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

//    private val productAdapter: ListProductAdapter by lazy {
//        ListProductAdapter()
//    }

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
    }

    private fun updateList() {
        viewModel.products.observe(requireActivity()) { product ->
            setProducts(product.data)
        }

//        viewModel.products.observe(requireActivity()) {
//            productAdapter.submitData(lifecycle, it)
////            findViewById<TextView>(R.id.tv_empty_list).visibility =
////                if (it.isEmpty()) View.VISIBLE else View.GONE
//        }
    }

    private fun setProducts(products: List<ProductItem>) {

        val adapter = ListProductAdapter()
        adapter.submitList(products)
        binding.rvProductPopular.adapter = adapter

        adapter.setOnItemClickCallback(object : ListProductAdapter.OnItemClickCallback {
            override fun onItemClicked(product: ProductItem) {
//                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
//                intentToDetail.putExtra(EXTRA_STORY, story)
//                startActivity(intentToDetail)
            }
        })
    }


    private fun onProductClick(product: ProductItem) {
        //TODO : show detailed product

    }
}
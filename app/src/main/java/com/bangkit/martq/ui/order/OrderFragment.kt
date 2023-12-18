package com.bangkit.martq.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.data.local.room.Cart
import com.bangkit.martq.data.model.ProductOrder
import com.bangkit.martq.data.remote.response.PesananItem
import com.bangkit.martq.databinding.FragmentOrderBinding
import com.bangkit.martq.databinding.LayoutOrderCompleteDataBinding
import com.bangkit.martq.databinding.LayoutOrderReviewBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.carts.ListCartAdapter
import com.bangkit.martq.paging.orderReview.ListOrderReviewAdapter
import com.bangkit.martq.utils.ResultState
import com.google.android.material.bottomsheet.BottomSheetDialog

class OrderFragment : Fragment() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var orderReviewBinding: LayoutOrderReviewBinding
    private lateinit var completeDataBinding: LayoutOrderCompleteDataBinding

    private var _binding: FragmentOrderBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getOrderHistory(2).observe(requireActivity()) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    if (resultState.data.pesanan!!.isNotEmpty()) {
                        setupOrderHistory(resultState.data!!.pesanan!!)
                    }
                }
                is ResultState.Loading -> {
                }
                is ResultState.Error -> {
                }
            }

        }

        setupView()
        setUpCartList()
        updateList()
        setupOrderFlow()

        return root
    }

    private fun setupView() {
        with(binding) {
            sectionCart.btnDelivery.setOnClickListener {
                Toast.makeText(requireContext(),
                    "Ups! Mohon maaf untuk saat ini kami belum menyediakan jasa delivery :(",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOrderHistory(orderHistories: List<PesananItem?>) {
        with(binding) {
            sectionOrderStatus.root.visibility = View.VISIBLE

            sectionOrderStatus.tvStatus.text = orderHistories[0]?.statusPembayaran
            sectionOrderStatus.tvOrderPrice.text = orderHistories[0]?.totalHarga.toString()
        }
    }

    private fun setUpCartList() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.sectionCart.rvCartList.layoutManager = layoutManager
    }

    private fun updateList() {
        viewModel.products.observe(requireActivity()) { product ->
            if (product.isNotEmpty()) {
                with(binding.sectionCart) {
                    rvCartList.visibility = View.VISIBLE
                    btnCheckout.visibility = View.VISIBLE
                    emptyState.visibility = View.INVISIBLE
                }

                setProducts(product)
            } else {
                with(binding.sectionCart) {
                    rvCartList.visibility = View.INVISIBLE
                    btnCheckout.visibility = View.INVISIBLE
                    emptyState.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setProducts(products: List<Cart>) {

        val adapter = ListCartAdapter()
        adapter.submitList(products)
        binding.sectionCart.rvCartList.adapter = adapter

        adapter.setOnItemClickCallback(object : ListCartAdapter.OnItemClickCallback {
            override fun onBtnAddClicked(cart: Cart) {
            }

            override fun onBtnSubtractClicked(cart: Cart) {
            }
        })
    }

    fun setupOrderFlow() {
        bottomSheetDialog = BottomSheetDialog(requireContext())

        orderReviewBinding = LayoutOrderReviewBinding.inflate(layoutInflater)
        completeDataBinding = LayoutOrderCompleteDataBinding.inflate(layoutInflater)

        binding.sectionCart.btnCheckout.setOnClickListener {
            bottomSheetDialog.setContentView(orderReviewBinding.root)
            bottomSheetDialog.show()
        }

        setupOrderReview()
    }

    fun setupOrderReview() {
        val layoutManager2 = LinearLayoutManager(requireContext())
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        orderReviewBinding.rvProductOrder.layoutManager = layoutManager2

        viewModel.products.observe(requireActivity()) { products ->

            val productsOrder = mutableListOf<ProductOrder>()
            var totalPrice = 0

            for (i in products) {
                val productOrder = ProductOrder(i.productName, i.price, i.quantity, i.price * i.quantity)
                productsOrder.add(productOrder)

                totalPrice += i.price * i.quantity
            }

            val adapter2 = ListOrderReviewAdapter()
            adapter2.submitList(productsOrder)

            orderReviewBinding.rvProductOrder.adapter = adapter2
            orderReviewBinding.tvTotalPriceValue.text = (totalPrice + 7000).toString()
        }

        orderReviewBinding.btnNext.setOnClickListener {
            viewModel.getSession().observe(requireActivity()) { user ->
                if (user.email != "") {
                    val totalHarga = orderReviewBinding.tvTotalPriceValue.text.toString().toInt()

                    // TODO: waiting API
                    val products = listOf(4, 4)
                    setupCompleteData(products, totalHarga)
                } else {
                    bottomSheetDialog.dismiss()

                    Toast.makeText(requireContext(),
                        "Demi menjaga keamanan, mohon untuk login terlebih dahulu sebelum memesan :)",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    fun setupCompleteData(products: List<Int>, totalPrice: Int) {

        bottomSheetDialog.setContentView(completeDataBinding.root)

        completeDataBinding.btnMakeOrder.setOnClickListener {
            viewModel.makeOrder(
                2,
                "false",
                null,
                1,
                7000,
                totalPrice,
                products
            )

            bottomSheetDialog.dismiss()

            Toast.makeText(requireContext(), "Yay! Berhasil membuat pesanan.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
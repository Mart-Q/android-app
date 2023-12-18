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
import com.bangkit.martq.databinding.FragmentOrderBinding
import com.bangkit.martq.databinding.LayoutOrderCompleteDataBinding
import com.bangkit.martq.databinding.LayoutOrderReviewBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.carts.ListCartAdapter
import com.bangkit.martq.paging.orderReview.ListOrderReviewAdapter
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

        setUpRecycler()
        updateList()
        setupOrderFlow()

        return root
    }

    private fun setUpRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.sectionCart.rvCartList.layoutManager = layoutManager
    }

    private fun updateList() {
        viewModel.products.observe(requireActivity()) { product ->
            setProducts(product)
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
            val totalHarga = orderReviewBinding.tvTotalPriceValue.text.toString().toInt()
            // TODO: waiting API
            val products = listOf(4, 4)
            setupCompleteData(products, totalHarga)
        }
    }

    fun setupCompleteData(products: List<Int>, totalPrice: Int) {

        bottomSheetDialog.setContentView(completeDataBinding.root)

        viewModel.getUserPhone().observe(requireActivity()) { phone ->
            completeDataBinding.etPhoneNumber.setText(phone)
        }

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
}
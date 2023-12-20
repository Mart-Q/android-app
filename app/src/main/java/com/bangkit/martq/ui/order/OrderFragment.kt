package com.bangkit.martq.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.data.local.datastore.UserModel
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

    private fun setupOrderHistory(orderHistories: List<PesananItem?>, totalPrice: Int) {
        with(binding) {
            sectionOrderStatus.root.visibility = View.VISIBLE

            sectionOrderStatus.tvStatus.text = orderHistories[orderHistories.size - 1]?.status
            sectionOrderStatus.tvOrderPrice.text = totalPrice.toString()
            sectionOrderStatus.tvMarketName.text = orderHistories[orderHistories.size - 1]?.idMarket
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
                Toast.makeText(requireContext(), "Add", Toast.LENGTH_SHORT).show()
            }

            override fun onBtnSubtractClicked(cart: Cart) {
                Toast.makeText(requireContext(), "snkajndkkkkkkkk", Toast.LENGTH_SHORT).show()
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

        val productsOrder = mutableListOf<ProductOrder>()
        var productNames = ""

        viewModel.products.observe(requireActivity()) { products ->

            var totalPrice = 0

            for (i in products) {
                val productOrder = ProductOrder(i.productName, i.price, i.quantity, i.price * i.quantity)
                productsOrder.add(productOrder)
                productNames += i.productName

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
                    val userCreds = UserModel(
                        user.email,
                        user.name,
                        user.phone,
                        user.address,
                    )

                    setupCompleteData(
                        productNames,
                        totalHarga,
                        userCreds
                    )
                } else {
                    bottomSheetDialog.dismiss()

                    Toast.makeText(requireContext(),
                        "Mohon untuk login terlebih dahulu sebelum memesan :)",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun setupCompleteData(products: String, totalPrice: Int, userCreds: UserModel) {

        bottomSheetDialog.setContentView(completeDataBinding.root)

        completeDataBinding.tvAddressValue.text = userCreds.address
        completeDataBinding.etPhoneNumber.setText(userCreds.phone)
        completeDataBinding.etName.setText(userCreds.name)

        completeDataBinding.btnMakeOrder.setOnClickListener {
            Log.d("DEBUGGG", "setupCompleteData: products: $products")
            viewModel.makeOrder(
                7,
                "false",
                2,
                1,
                7000,
                totalPrice,
                "Menghubungi pihak pasar",
                products
            ).observe(requireActivity()) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        viewModel.deleteCart()
                        Toast.makeText(requireContext(), "Yay! Berhasil membuat pesanan.", Toast.LENGTH_SHORT).show()
                    }
                    is ResultState.Loading -> {
                    }
                    is ResultState.Error -> {
                        viewModel.deleteCart()
                        Toast.makeText(requireContext(), "Pesanan berhasil dibuat.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            getOrderHistory(totalPrice)

            bottomSheetDialog.dismiss()
        }
    }

    private fun getOrderHistory(totalPrice: Int) {
        viewModel.getOrderHistory(7).observe(requireActivity()) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    if (resultState.data.pesanan!!.isNotEmpty()) {
                        setupOrderHistory(resultState.data!!.pesanan!!, totalPrice)
                    }
                }
                is ResultState.Loading -> {
                }
                is ResultState.Error -> {
                }
            }
        }
    }

}
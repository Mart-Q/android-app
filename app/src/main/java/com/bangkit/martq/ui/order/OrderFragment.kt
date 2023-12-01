package com.bangkit.martq.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.martq.databinding.FragmentOrderBinding
import com.bangkit.martq.databinding.LayoutOrderCompleteDataBinding
import com.bangkit.martq.databinding.LayoutOrderReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class OrderFragment : Fragment() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var orderReviewBinding: LayoutOrderReviewBinding
    private lateinit var completeDataBinding: LayoutOrderCompleteDataBinding

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderViewModel =
            ViewModelProvider(this).get(OrderViewModel::class.java)

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupOrderFlow()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupOrderFlow() {
        bottomSheetDialog = BottomSheetDialog(requireContext())

        orderReviewBinding = LayoutOrderReviewBinding.inflate(layoutInflater)
        completeDataBinding = LayoutOrderCompleteDataBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(orderReviewBinding.root)

        binding.sectionCart.btnCheckout.setOnClickListener {
            bottomSheetDialog.show()
        }

        orderReviewBinding.btnNext.setOnClickListener {
            bottomSheetDialog.dismiss()

            bottomSheetDialog.setContentView(completeDataBinding.root)
            bottomSheetDialog.show()
        }

        completeDataBinding.btnMakeOrder.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }
}
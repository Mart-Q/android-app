package com.bangkit.martq.ui.recommencation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.martq.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment() {

    companion object {
        fun newInstance() = RecommendationFragment()
    }

    private lateinit var viewModel: RecommendationViewModel

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecommendationViewModel::class.java)
        // TODO: Use the ViewModel
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
        }
    }

}
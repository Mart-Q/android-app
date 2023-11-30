package com.bangkit.martq.ui.recommencation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.martq.R

class RecommendationFragment : Fragment() {

    companion object {
        fun newInstance() = RecommendationFragment()
    }

    private lateinit var viewModel: RecommendationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecommendationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
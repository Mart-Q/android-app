package com.bangkit.martq.ui.recommencation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.martq.databinding.FragmentRecommendationBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.paging.recipe.ListRecipeAdapter
import com.bangkit.martq.ui.recipe.RecipeActivity
import com.bangkit.martq.utils.ResultState
import com.google.android.material.chip.Chip

class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecommendationViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()
        setUpFoodList()

        return root
    }

    private fun setupView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()

                    chipIngredient.addView(Chip(requireContext(), null, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Entry).apply {
                        text = searchView.text
                        isCloseIconVisible = true
                        setOnCloseIconClickListener { chipIngredient.removeView(this) }
                    })

                    false
                    }

            btnExploreRecipe.setOnClickListener{
                updateList()
            }
        }
    }

    private fun setUpFoodList() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvRecipes.layoutManager = layoutManager
    }

    private fun updateList() {
        viewModel.getFoodInspiration().observe(requireActivity()) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    setRecipes(resultState.data.recommendations!!)
                }
                is ResultState.Loading -> {
                }
                is ResultState.Error -> {
                }
            }
        }
    }

    private fun setRecipes(recipes: List<String?>) {

        val adapter = ListRecipeAdapter()
        adapter.submitList(recipes)
        binding.rvRecipes.adapter = adapter

        adapter.setOnItemClickCallback(object : ListRecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: String) {
                val intent = Intent(requireContext(), RecipeActivity::class.java)
                intent.putExtra(EXTRA_RECIPE, recipe)
                startActivity(intent)
            }
        })
    }

    companion object {
        private val EXTRA_RECIPE = "extra_recipe"
    }
}
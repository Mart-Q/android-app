package com.bangkit.martq.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.databinding.ActivityRecipeBinding


class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: get data from ML model
    }
}
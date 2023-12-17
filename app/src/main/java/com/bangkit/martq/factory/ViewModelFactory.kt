package com.bangkit.martq.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.martq.di.Injection
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.ui.home.HomeViewModel
import com.bangkit.martq.ui.productDetail.ProductDetailViewModel
import com.bangkit.martq.ui.productPage.ProductsViewModel
import com.bangkit.martq.ui.recipe.RecipeViewModel

class ViewModelFactory(private val productRepo: ProductRepository, private val categoryRepo: ProductCategoryRepository, private val cartRepo: CartRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(productRepo, categoryRepo) as T
            }
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                RecipeViewModel(productRepo) as T
            }
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(productRepo, cartRepo) as T
            }
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                ProductsViewModel(productRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideProductRepository(context),
                        Injection.provideProductCategoryRepository(context),
                        Injection.provideCartRepository(context.applicationContext as Application)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
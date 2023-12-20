package com.bangkit.martq.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.martq.di.Injection
import com.bangkit.martq.repository.AuthRepository
import com.bangkit.martq.repository.CartRepository
import com.bangkit.martq.repository.OrderRepository
import com.bangkit.martq.repository.ProductCategoryRepository
import com.bangkit.martq.repository.ProductRepository
import com.bangkit.martq.repository.ProfileRepository
import com.bangkit.martq.repository.RecipeRepository
import com.bangkit.martq.ui.account.AccountViewModel
import com.bangkit.martq.ui.home.HomeViewModel
import com.bangkit.martq.ui.login.LoginViewModel
import com.bangkit.martq.ui.order.OrderViewModel
import com.bangkit.martq.ui.productDetail.ProductDetailViewModel
import com.bangkit.martq.ui.productPage.ProductsViewModel
import com.bangkit.martq.ui.recipe.RecipeViewModel
import com.bangkit.martq.ui.recommencation.RecommendationViewModel
import com.bangkit.martq.ui.register.RegisterViewModel

class ViewModelFactory(
    private val productRepo: ProductRepository,
    private val categoryRepo: ProductCategoryRepository,
    private val cartRepo: CartRepository,
    private val profileRepo: ProfileRepository,
    private val orderRepo: OrderRepository,
    private val authRepo: AuthRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(productRepo, categoryRepo) as T
            }
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                RecipeViewModel(cartRepo) as T
            }
            modelClass.isAssignableFrom(ProductDetailViewModel::class.java) -> {
                ProductDetailViewModel(productRepo, cartRepo) as T
            }
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                ProductsViewModel(productRepo) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(cartRepo, profileRepo, orderRepo) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(profileRepo) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(profileRepo, authRepo) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(profileRepo) as T
            }
            modelClass.isAssignableFrom(RecommendationViewModel::class.java) -> {
                RecommendationViewModel(recipeRepository) as T
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
                        Injection.provideCartRepository(context.applicationContext as Application),
                        Injection.provideProfileRepository(context),
                        Injection.provideOrderRepository(context),
                        Injection.provideAuthRepository(context),
                        Injection.provideRecipeRepository(context),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
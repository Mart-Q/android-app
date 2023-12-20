package com.bangkit.martq.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.databinding.ActivityLoginBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.ui.register.RegisterActivity
import com.bangkit.martq.utils.ResultState

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email != "" && password != "") {
                viewModel.login(email, password).observe(this) { resultState ->
                    when (resultState) {
                        is ResultState.Success -> {
                            showLoading(false)
                        }
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(resultState.error)
                        }
                    }
                }

                viewModel.saveSession(
                    UserModel(
                        email = email,
                        name = "",
                        phone = "",
                        address = ""
                    ))
                finish()
            } else {
                Toast.makeText(this, "Mohon isi semua data dulu ya.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDaftar.setOnClickListener{
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)

            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
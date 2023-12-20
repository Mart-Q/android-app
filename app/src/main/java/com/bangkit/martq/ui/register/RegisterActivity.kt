package com.bangkit.martq.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.databinding.ActivityRegisterBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        binding.btnDaftar.setOnClickListener{

            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()

            if (email != "" && name != "" && password != "") {
                viewModel.saveSession(
                    UserModel(
                        email = binding.etEmail.text.toString(),
                        name = binding.etName.text.toString(),
                        phone = "",
                        address = ""
                    )
                )
                Toast.makeText(this, "Yay. Berhasil mendaftar.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Mohon isi semua data dulu ya.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)

            finish()
        }
    }
}
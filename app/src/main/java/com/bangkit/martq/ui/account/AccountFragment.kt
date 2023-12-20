package com.bangkit.martq.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.martq.data.local.datastore.UserModel
import com.bangkit.martq.databinding.FragmentAccountBinding
import com.bangkit.martq.factory.ViewModelFactory
import com.bangkit.martq.ui.login.LoginActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSession().observe(requireActivity()) { user ->
            val userCreds = UserModel(
                user.email,
                user.name,
                user.phone,
                user.address
            )

            setupView(userCreds)
            setupAction(userCreds)
        }

        return root
    }

    private fun setupView(userCreds: UserModel) {
        if (userCreds.email != "") {
            with(binding) {
                layoutLogin.root.visibility = View.VISIBLE
                layoutNonLogin.root.visibility = View.GONE

                isOnEditState("phone", false, userCreds)
                isOnEditState("address", false, userCreds)

                layoutLogin.tvName.text = userCreds.name
                layoutLogin.tvEmail.text = userCreds.email

                btnAuth.visibility = View.VISIBLE
                btnAuth.text = "Logout"
            }
        } else {
            with(binding) {
                layoutLogin.root.visibility = View.GONE
                layoutNonLogin.root.visibility = View.VISIBLE

                btnAuth.visibility = View.GONE
            }
        }
    }

    private fun setupAction(userCreds: UserModel) {
        with(binding) {
            layoutLogin.btnEdtPhone.setOnClickListener(View.OnClickListener {
                isOnEditState("phone", true, userCreds)
            })

            layoutLogin.btnEdtAddress.setOnClickListener(View.OnClickListener {
                isOnEditState("address", true, userCreds)
            })

            layoutLogin.btnSavePhone.setOnClickListener(View.OnClickListener {
                isOnEditState("phone", false, userCreds)

                val newPhone = layoutLogin.etPhone.text.toString()
                viewModel.savePhone(newPhone)
            })

            layoutLogin.btnSaveAddress.setOnClickListener(View.OnClickListener {
                isOnEditState("address", false, userCreds)

                val newAddress = layoutLogin.etAddress.text.toString()
                viewModel.saveAddress(newAddress)
            })

            layoutNonLogin.root.setOnClickListener(View.OnClickListener {
                val intentLogin = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intentLogin)
            })

            btnAuth.setOnClickListener(View.OnClickListener {
                viewModel.logout()
            })
        }
    }

    private fun isOnEditState(field: String, isOnEdit: Boolean, userCreds: UserModel ) {
        if (isOnEdit && field == "address") {
            with(binding) {
                layoutLogin.tvAddress.visibility = View.INVISIBLE
                layoutLogin.btnEdtAddress.visibility = View.INVISIBLE
                layoutLogin.etAddress.visibility = View.VISIBLE
                layoutLogin.btnSaveAddress.visibility = View.VISIBLE

                layoutLogin.etAddress.setText(userCreds.address)
            }
        } else if (isOnEdit && field == "phone") {
            with(binding) {
                layoutLogin.tvPhone.visibility = View.INVISIBLE
                layoutLogin.btnEdtPhone.visibility = View.INVISIBLE
                layoutLogin.etPhone.visibility = View.VISIBLE
                layoutLogin.btnSavePhone.visibility = View.VISIBLE

                layoutLogin.etPhone.setText(userCreds.phone)
            }
        } else if (!isOnEdit && field == "address") {
            with(binding) {
                layoutLogin.tvAddress.visibility = View.VISIBLE
                layoutLogin.btnEdtAddress.visibility = View.VISIBLE
                layoutLogin.etAddress.visibility = View.INVISIBLE
                layoutLogin.btnSaveAddress.visibility = View.INVISIBLE

                layoutLogin.tvAddress.text = userCreds.address
            }
        }
        else {
            with(binding) {
                layoutLogin.tvPhone.visibility = View.VISIBLE
                layoutLogin.btnEdtPhone.visibility = View.VISIBLE
                layoutLogin.etPhone.visibility = View.INVISIBLE
                layoutLogin.btnSavePhone.visibility = View.INVISIBLE

                layoutLogin.tvPhone.text = userCreds.phone
            }
        }
    }
}
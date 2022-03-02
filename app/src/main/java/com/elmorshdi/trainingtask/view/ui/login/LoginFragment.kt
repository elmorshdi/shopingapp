package com.elmorshdi.trainingtask.view.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.databinding.LoginFragmentBinding
import com.elmorshdi.trainingtask.helper.isEmailValid
import com.elmorshdi.trainingtask.helper.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            validations()
        }

    }

    private fun clearError() {
        binding.loginEmailTextField.isErrorEnabled = false
        binding.loginPasswordTextField.boxStrokeColor = Color.parseColor("#FF3E497A")
        binding.errorTextView.visibility = View.INVISIBLE
    }

    private fun validations() {
        clearError()

        when {
            binding.loginEmailEditText.text?.trim()?.isEmailValid() == false -> {
                binding.loginEmailTextField.error = getString(R.string.enter_valid_email)
            }
            binding.loginPasswordEditText.text?.isValidPassword() == false -> {
                binding.loginPasswordTextField.boxStrokeColor = Color.RED
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = getString(R.string.Invalid_password)
            }
            else -> {
                val email = binding.loginEmailEditText.text?.trim().toString()
                val password = binding.loginPasswordEditText.text?.trim().toString()
                viewModel.login(email, password, binding.loginButton)

            }
        }

    }


}
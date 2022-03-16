package com.elmorshdi.trainingtask.view.ui.login

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.databinding.LoginFragmentBinding
import com.elmorshdi.trainingtask.helper.toast
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEditText.text?.trim().toString()
            val password = binding.loginPasswordEditText.text?.trim().toString()
            viewModel.login(email, password)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.loginUiState.collect { event ->
                when (event) {
                    is LoginViewModel.LoginUiState.Success -> {
                        binding.loginSpinKit.isVisible = false
                        SharedPreferencesManager.signInShared(
                            sharedPreferences.edit(),
                            event.token,
                            event.name
                        )
                        navigateToMain()
                    }
                    is LoginViewModel.LoginUiState.Error -> {
                        clearError()
                        binding.loginSpinKit.isVisible = false
                        when (event.error) {
                            is LoginViewModel.Error.NetworkError -> {
                                requireContext().toast(event.error.errorMessage)
                            }
                            is LoginViewModel.Error.PasswordNotValid -> {
                                binding.loginPasswordTextField.boxStrokeColor = Color.RED
                                binding.errorTextView.isVisible = true
                                binding.errorTextView.text = getString(R.string.Invalid_password)
                            }
                            is LoginViewModel.Error.EmailNotValid -> {
                                binding.loginEmailTextField.error =
                                    getString(R.string.enter_valid_email)
                            }
                            else -> Unit
                        }

                    }
                    LoginViewModel.LoginUiState.Loading -> {
                        clearError()
                        binding.loginSpinKit.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun clearError() {
        binding.loginEmailTextField.isErrorEnabled = false
        binding.loginPasswordTextField.boxStrokeColor = Color.parseColor("#FF3E497A")
        binding.errorTextView.isVisible = false
    }

    private fun navigateToMain() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        binding.root.findNavController().navigate(action)
    }

}
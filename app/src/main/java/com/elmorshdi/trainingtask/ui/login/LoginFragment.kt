package com.elmorshdi.trainingtask.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.LoginFragmentBinding
import com.elmorshdi.trainingtask.util.Constant.observeOnce
import com.elmorshdi.trainingtask.util.Constant.setProgressBar
import com.elmorshdi.trainingtask.util.SharedPreferencesManager.signInShared

class LoginFragment : Fragment() {


    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.loginButton.setOnClickListener {
            validations()
        }

    }

    private fun validations() {
        when {
            binding.loginEmailEditText.text?.trim()?.isEmpty() == true -> {
                binding.loginEmailTextField.error = " Enter Email "
            }
            binding.loginPasswordEditText.text?.trim()?.isEmpty() == true -> {
                binding.loginPasswordTextField.error = " Enter Password  "
            }
            else -> {
                val email = binding.loginEmailEditText.text?.trim().toString()
                val password = binding.loginPasswordEditText.text?.trim().toString()
                when {
                    password.length <= 7 -> {
                        binding.loginPasswordTextField.error = "Wrong Password"
                    }
                    else -> {
                        login(email, password)
                    }
                }
            }
        }

    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password)
        val progressBar = setProgressBar(binding.loginSpinKit)
        viewModel.loginState.observeOnce(viewLifecycleOwner, Observer { status ->
            if (status) {
                signInShared(requireContext(), viewModel.token!!, viewModel.username!!)
                removeFromBackStack()
                navigateToMain()
                progressBar.visibility = View.INVISIBLE
            } else {
                viewModel.stateCodeMessage.observeOnce(viewLifecycleOwner, Observer {

                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                })
            }
        })
/*
        viewModel.loginState.observe(viewLifecycleOwner) { status ->
            if (status) {
                signInShared(requireContext(), viewModel.token!!, viewModel.username!!)
                removeFromBackStack()
                navigateToMain()
                progressBar.visibility = View.INVISIBLE

            } else {
                viewModel.stateCodeMessage.observe(viewLifecycleOwner) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }

            }
        }
*/
    }

    private fun navigateToMain() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        binding.root.findNavController().navigate(action)
    }


    private fun removeFromBackStack() {
//        TODO("Not yet implemented")
    }

}
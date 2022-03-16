package com.elmorshdi.trainingtask.view.ui.addproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.FragmentAddItemBinding
import com.elmorshdi.trainingtask.helper.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
open class AddItemFragment : Fragment() {
    private val viewModel: AddItemViewModel by viewModels()
    private lateinit var binding: FragmentAddItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            viewModel.postProduct(
                binding.addNameEditText.text?.trim().toString(),
                binding.addPriceEditText.text?.trim().toString(),
                binding.addQuantityEditText.text?.trim().toString()
            )
        }
        binding.addBackArrow.setOnClickListener {
            navigateToMain()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.addItemUiState.collect { event ->
                when (event) {
                    is AddItemViewModel.AddItemUiState.Success -> {
                        binding.addSpinKit.isVisible = false
                        navigateToMain()
                        requireContext().toast("Product Added")
                    }
                    is AddItemViewModel.AddItemUiState.Error -> {
                        clearError()
                        binding.addSpinKit.isVisible = false
                        when (event.error) {
                            is AddItemViewModel.AddItemError.NetworkError -> {
                                requireContext().toast(event.error.errorMessage)
                            }
                            is AddItemViewModel.AddItemError.NameNotValid -> {
                                binding.addNameTextField.error =
                                    "Invalid name can't be a blank or a number"
                            }
                            is AddItemViewModel.AddItemError.PriceNotValid -> {
                                binding.addPriceTextField.error =
                                    "Invalid price can't be empty or zero"
                            }
                            is AddItemViewModel.AddItemError.QuantityNotValid -> {
                                binding.addQuantityTextField.error =
                                    "Invalid quantity cannot be empty or zero"
                            }
                        }
                    }
                    is AddItemViewModel.AddItemUiState.Loading -> {
                        clearError()
                        binding.addSpinKit.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
    private fun navigateToMain() {
        val action = AddItemFragmentDirections.actionAddItemFragmentToMainFragment()
        binding.root.findNavController().navigate(action)
    }
    private fun clearError() {
        binding.addNameTextField.isErrorEnabled = false
        binding.addPriceTextField.isErrorEnabled = false
        binding.addQuantityTextField.isErrorEnabled = false
    }
}
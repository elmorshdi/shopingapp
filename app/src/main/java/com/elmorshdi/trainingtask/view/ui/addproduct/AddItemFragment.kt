package com.elmorshdi.trainingtask.view.ui.addproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.FragmentAddItemBinding
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.helper.observeOnce
import com.elmorshdi.trainingtask.helper.setProgressBar
import dagger.hilt.android.AndroidEntryPoint

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
            validations()
        }
        binding.addBackArrow.setOnClickListener {
            navigateToMain(view)
        }
    }

    private fun navigateToMain(view: View) {
        val action = AddItemFragmentDirections.actionAddItemFragmentToMainFragment()
        view.findNavController().navigate(action)
    }

    private fun validations() {
        clearError()
        when {

            binding.addNameEditText.text?.trim()?.isEmpty() == true -> {
                binding.addNameTextField.error = " Enter Name "
            }
            binding.addNameEditText.text?.trim()?.isDigitsOnly() == true -> {
                binding.addNameTextField.error = " Invalid Name "
            }
            binding.addPriceEditText.text?.trim()?.isEmpty() == true -> {
                binding.addPriceTextField.error = " Enter Price  "
            }
            binding.addQuantityEditText.text?.trim()?.isEmpty() == true -> {
                binding.addQuantityTextField.error = " Enter Quantity "
            }
            else -> {
                val price = binding.addPriceEditText.text?.trim().toString()
                val quantity = binding.addQuantityEditText.text?.trim().toString()
                val name = binding.addNameEditText.text?.trim().toString()
                when {
                    price == "0" || price == "0.0" -> {
                        binding.addPriceTextField.error = "Price can't be zero"
                    }
                    quantity == "0" || quantity == "0.0" -> {
                        binding.addQuantityTextField.error = "Quantity can't be zero"
                    }
                    else -> {
                        val product = Product(
                            name = name,
                            price = price.toInt(),
                            quantity = quantity.toInt()
                        )
                        postProduct(product)
                    }

                }
            }

        }


    }

    private fun clearError() {
        binding.addNameTextField.isErrorEnabled = false
        binding.addNameTextField.isErrorEnabled = false
        binding.addNameTextField.isErrorEnabled = false
    }

    private fun postProduct(product: Product) {
        viewModel.postProduct(product)
        val progressBar = setProgressBar(binding.addSpinKit)
        viewModel.addState.observeOnce(viewLifecycleOwner) { status ->
            if (status) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Product Added", Toast.LENGTH_LONG).show()
                navigateToMain(binding.root)
            } else {
                viewModel.stateCodeMessage.observeOnce(viewLifecycleOwner) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }

    }



}
package com.elmorshdi.trainingtask.ui.addproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.FragmentAddItemBinding
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.ui.mainpage.MainFragmentDirections
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce


class AddItemFragment : Fragment() {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var viewModel: AddItemViewModel
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
        viewModel = ViewModelProvider(this)[AddItemViewModel::class.java]
        binding.addButton.setOnClickListener {
            validations()
        }
        val action = AddItemFragmentDirections.actionAddItemFragmentToMainFragment()
        binding.addBackArrow.setOnClickListener {
            view.findNavController().navigate(action)
        }
    }

    private fun validations() {
        clearError()
        when {

            binding.addNameEditText.text?.trim()?.isEmpty() == true -> {
                binding.addNameTextField.error = " Enter Name "
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
        val progressBar = binding.addSpinKit as ProgressBar
        val doubleBounce: Sprite = DoubleBounce()
        progressBar.indeterminateDrawable = doubleBounce
        progressBar.visibility = View.VISIBLE
        viewModel.addState.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                progressBar.visibility = View.INVISIBLE

                 Toast.makeText(requireContext(), "Product Added", Toast.LENGTH_LONG).show()

                clearPage()
            } else {
                viewModel.stateCode.observe(viewLifecycleOwner, Observer {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Err0r:$it", Toast.LENGTH_LONG).show()
                })

            }
        })


    }

    private fun clearPage() {
        binding.addNameEditText.setText("")
        binding.addPriceEditText.setText("")
        binding.addQuantityEditText.setText("")

    }



}
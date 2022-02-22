package com.elmorshdi.trainingtask.ui.productview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.databinding.ProductFragmentBinding
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.util.Constant.alertDialog
import com.elmorshdi.trainingtask.util.Constant.observeOnce
import com.elmorshdi.trainingtask.util.Constant.setProgressBar
import com.google.android.material.transition.MaterialSharedAxis

class ProductViewFragment : Fragment() {


    private lateinit var binding: ProductFragmentBinding
    private val args: ProductViewFragmentArgs by navArgs()

    private lateinit var viewModel: ProductViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductViewViewModel::class.java]
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
        setViews(args.product)
        binding.editBackArrow.setOnClickListener {
            navigateToMain(view)
        }
        binding.deleteButton.setOnClickListener {

            alertDialog(
                getString(R.string.dialogTitle),
                getString(R.string.dialogMessage),
                requireContext(),
                ::delete, args.product.id!!
            )
        }
    }

    private fun navigateToMain(view: View) {
        val action = ProductViewFragmentDirections.actionProductFragmentToMainFragment()
        view.findNavController().navigate(action)
    }


    private fun delete(id: Int) {
        viewModel.delete(id)
        val progressBar = setProgressBar(binding.viewSpinKit)
        viewModel.deleteState.observeOnce(viewLifecycleOwner, Observer { status ->
            if (status) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "deleted", Toast.LENGTH_LONG).show()
                navigateToMain(binding.root)
            } else {
                viewModel.stateCode.observeOnce(viewLifecycleOwner, Observer {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                })

            }
        })
    }



    private fun setViews(product: Product) {
        binding.itemPrice.text = product.price.toString()
        binding.itemName.text = product.name.toString()
        binding.itemQuantityValue.text = product.quantity.toString()
        Glide.with(binding.itemImage.context).load(product.image).into(binding.itemImage)

    }

}
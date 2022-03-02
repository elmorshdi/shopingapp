package com.elmorshdi.trainingtask.view.ui.productview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.databinding.ProductFragmentBinding
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.helper.alertDialog
import com.elmorshdi.trainingtask.helper.observeOnce
import com.elmorshdi.trainingtask.helper.setProgressBar
import com.elmorshdi.trainingtask.view.ui.mainpage.MainViewModel
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductViewFragment : Fragment() {


    private lateinit var binding: ProductFragmentBinding
    private val args: ProductViewFragmentArgs by navArgs<ProductViewFragmentArgs>()

    private  val viewModel: ProductViewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        binding.product=args.product
        binding.viewModel=viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)


    }



//    private fun delete(id: Int) {
//        viewModel.delete(id)
//        val progressBar = setProgressBar(binding.viewSpinKit)
//        viewModel.deleteState.observeOnce(viewLifecycleOwner, Observer { status ->
//            if (status) {
//                progressBar.visibility = View.INVISIBLE
//                Toast.makeText(requireContext(), "deleted", Toast.LENGTH_LONG).show()
//                navigateToMain(binding.root)
//            } else {
//                viewModel.stateCode.observeOnce(viewLifecycleOwner, Observer {
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
//                })
//
//            }
//        })
//    }





}
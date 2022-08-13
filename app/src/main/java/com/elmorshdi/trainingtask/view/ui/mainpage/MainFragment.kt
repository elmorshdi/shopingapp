package com.elmorshdi.trainingtask.view.ui.mainpage

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.FragmentMainBinding
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.helper.SpacesItemDecoration
import com.elmorshdi.trainingtask.helper.alertDialog
import com.elmorshdi.trainingtask.view.adapter.GridProductAdapter
import com.elmorshdi.trainingtask.view.adapter.HorizontalProductAdapter
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager.getUsername
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint

class MainFragment : Fragment(), GridProductAdapter.Interaction,
    HorizontalProductAdapter.Interaction {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainAddButton.setOnClickListener {
            addProduct(it)
        }
        binding.signOutArrow.setOnClickListener {
            alertDialog(
                "sign Out", "Are you sure you want to sign out ?",
                it.context, ::signOut, it
            )
        }
        viewModel.getProductsList()
            lifecycleScope.launchWhenCreated {

                viewModel.mainUiState.collect { event ->
                    when (event) {
                        is MainViewModel.MainUiState.Loading ->
                            binding.spinKit.isVisible = true
                        is MainViewModel.MainUiState.Error -> {
                            binding.spinKit.isVisible = false
                            binding.mainRecycler.isVisible = false
                            binding.errorText.isVisible = true
                            binding.errorText.text = event.error
                        }
                        is MainViewModel.MainUiState.Success -> {
                            binding.spinKit.isVisible = false
                            binding.errorText.isVisible = false
                            setUpRecyclerView()
                        }
                        else -> UInt
                    }
                }


            }

    }


    private fun setUpRecyclerView() {
        //Setup Grid recyclerView
        val itemDecoration = SpacesItemDecoration(10)
        binding.mainRecycler.addItemDecoration(itemDecoration)
        val adapter = GridProductAdapter(interaction = this)
        binding.mainRecycler.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.welcomeTextView.text = getUsername(sharedPreferences)
        return binding.root
    }

    private fun signOut(view: View) {
        SharedPreferencesManager.signOutShared(sharedPreferences.edit())
        val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
        view.findNavController().navigate(action)
    }

    private fun addProduct(view: View) {
        val action = MainFragmentDirections.actionMainFragmentToAddItemFragment()
        view.findNavController().navigate(action)
    }

    override fun onItemSelected(product: Product) {
        val action = MainFragmentDirections.actionMainFragmentToProductFragment(product)
        view?.findNavController()?.navigate(action)
    }
}
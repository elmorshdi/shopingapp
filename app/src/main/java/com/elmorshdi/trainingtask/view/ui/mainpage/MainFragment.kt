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
import com.elmorshdi.trainingtask.helper.showBottomSheet
import com.elmorshdi.trainingtask.view.adapter.GridProductAdapter
import com.elmorshdi.trainingtask.view.adapter.HorizontalProductAdapter
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
        binding.viewButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.mRecyclerShape.value = MainViewModel.RecyclerShape.Vertical
            } else {
                viewModel.mRecyclerShape.value = MainViewModel.RecyclerShape.Grid
            }

        }
        binding.sortButton.setOnClickListener {
            val bottomSheet = showBottomSheet(requireContext(), viewModel, layoutInflater)
            bottomSheet.setCanceledOnTouchOutside(true)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.recyclerShape.collect { event ->
                when (event) {
                    is MainViewModel.RecyclerShape.Grid -> {
                        binding.mainRecycler.visibility = View.VISIBLE
                        binding.mainRecyclerHor.visibility = View.INVISIBLE
                    }
                    is MainViewModel.RecyclerShape.Vertical -> {
                        binding.mainRecyclerHor.visibility = View.VISIBLE
                        binding.mainRecycler.visibility = View.INVISIBLE
                    }
                }

            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.mainUiState.collect { event ->
                when (event) {
                    is MainViewModel.MainUiState.Loading ->
                        binding.spinKit.isVisible = true
                    is MainViewModel.MainUiState.Error -> {
                        binding.spinKit.isVisible = false
                        binding.mainRecycler.isVisible = false
                        binding.mainRecyclerHor.isVisible = false
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
        //Setup Hor recyclerView
        val adapterHor = HorizontalProductAdapter(interaction = this)
        binding.mainRecyclerHor.adapter = adapterHor
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
        // setUpRecyclerView()
        binding.welcomeTextView.text = getUsername(sharedPreferences)
        return binding.root
    }

    override fun onItemSelected(product: Product) {
        val action = MainFragmentDirections.actionMainFragmentToProductFragment(product)
        view?.findNavController()?.navigate(action)
    }
}
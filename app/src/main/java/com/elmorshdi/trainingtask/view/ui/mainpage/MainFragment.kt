package com.elmorshdi.trainingtask.view.ui.mainpage

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.databinding.FragmentMainBinding
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.helper.showBottomSheet
import com.elmorshdi.trainingtask.view.adapter.GridProductAdapter
import com.elmorshdi.trainingtask.view.adapter.HorizontalProductAdapter
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager.getUsername
import dagger.hilt.android.AndroidEntryPoint
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

        binding.ViewButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.recyclerVisibility(isChecked)
        }


        binding.sortButton.setOnClickListener {
            val bottomSheet = showBottomSheet(requireContext(), viewModel, layoutInflater)
            bottomSheet.setCanceledOnTouchOutside(true)
        }
    }


    private fun setUpRecyclerView() {
        //Setup Hor recyclerView
        val adapterHor = HorizontalProductAdapter(interaction = this)
        binding.mainRecyclerHor.adapter = adapterHor
        //Setup Grid recyclerView
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
        setUpRecyclerView()
        binding.welcomeTextView.text = getUsername(sharedPreferences)
        return binding.root
    }

    override fun onItemSelected(product: Product) {
        val action = MainFragmentDirections.actionMainFragmentToProductFragment(product)
        view?.findNavController()?.navigate(action)
    }
}
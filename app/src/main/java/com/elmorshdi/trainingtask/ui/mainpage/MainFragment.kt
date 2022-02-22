package com.elmorshdi.trainingtask.ui.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmorshdi.trainingtask.adapter.ProductAdapter
import com.elmorshdi.trainingtask.databinding.FragmentMainBinding
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.util.Constant
import com.elmorshdi.trainingtask.util.Constant.TOKEN
import com.elmorshdi.trainingtask.util.Constant.alertDialog
import com.elmorshdi.trainingtask.util.Constant.observeOnce
import com.elmorshdi.trainingtask.util.SharedPreferencesManager
import com.elmorshdi.trainingtask.util.SharedPreferencesManager.getUsername
import com.elmorshdi.trainingtask.util.SharedPreferencesManager.signOutShared
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment(), ProductAdapter.Interaction {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.mainRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

        TOKEN = SharedPreferencesManager.getToken(requireContext())

        binding.signOutArrow.setOnClickListener {
            alertDialog(
                "sign Out", "Are you sure you want to sign out ?",
                requireContext(), ::signOut
            )
        }
        observeViewModel()
        binding.mainTabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                observeViewModel()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.welcomeTextView.text = "Welcome ${getUsername(requireContext())}"
        binding.mainAddButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddItemFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun signOut() {
        TOKEN = null
        signOutShared(requireContext())
        val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
        binding.root.findNavController().navigate(action)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeViewModel() {
        val progressBar = Constant.setProgressBar(binding.spinKit)
        viewModel.state.observeOnce(viewLifecycleOwner, Observer {
            if (it) {
                fetchData(progressBar)
            } else {
                viewModel.stateCodeMessage.observeOnce(viewLifecycleOwner, Observer { message ->
                    progressBar.visibility = View.INVISIBLE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = message
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                })
            }
        })


    }

    private fun fetchData(progressBar: ProgressBar) {
        binding.errorText.visibility = View.INVISIBLE
        var list: List<Product>
        viewLifecycleOwner.lifecycleScope.launch {
            when (binding.mainTabLayout.selectedTabPosition) {
                0 -> {
                    viewModel.stateFlow.collect {
                        list = it.filter { product -> product.price!! <= 10 }
                        val adapter = ProductAdapter(list, this@MainFragment)
                        binding.mainRecycler.adapter = adapter
                        progressBar.visibility = View.INVISIBLE
                    }
                }
                1 -> {
                    viewModel.stateFlow.collect {
                        list = it.filter { product -> product.price!! in 11..20 }
                        val adapter = ProductAdapter(list, this@MainFragment)
                        binding.mainRecycler.adapter = adapter
                        progressBar.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    viewModel.stateFlow.collect {
                        list = it.filter { product -> product.price!! >= 20 }
                        val adapter = ProductAdapter(list, this@MainFragment)
                        binding.mainRecycler.adapter = adapter
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }

        }
    }


    override fun onItemSelected(product: Product) {
        val action = MainFragmentDirections.actionMainFragmentToProductFragment(product)
        view?.findNavController()?.navigate(action)
    }
}
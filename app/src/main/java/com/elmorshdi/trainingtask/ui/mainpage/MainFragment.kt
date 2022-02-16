package com.elmorshdi.trainingtask.ui.mainpage

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmorshdi.trainingtask.ProductAdapter
import com.elmorshdi.trainingtask.databinding.FragmentMainBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FoldingCube


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

        binding.mainRecycler.layoutManager = layoutManager
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()

        val action = MainFragmentDirections.actionMainFragmentToAddItemFragment()
        binding.mainAddButton.setOnClickListener {
            view.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }
    private fun observeViewModel() {
        val progressBar = binding.spinKit as ProgressBar
        val doubleBounce: FoldingCube = FoldingCube	()
        progressBar.indeterminateDrawable = doubleBounce
        progressBar.visibility = View.VISIBLE
        viewModel.productList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                val adapter= ProductAdapter(it)
                binding.mainRecycler.adapter = adapter
                progressBar.visibility = View.INVISIBLE

            }
        })



    }


}
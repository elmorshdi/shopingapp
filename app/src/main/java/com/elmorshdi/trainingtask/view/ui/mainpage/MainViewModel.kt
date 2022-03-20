package com.elmorshdi.trainingtask.view.ui.mainpage

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.helper.alertDialog
import com.elmorshdi.trainingtask.view.util.Resource
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository
) : ViewModel() {
    val mainUiState: StateFlow<MainUiState>
        get() = _mainUiState
    private val _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    val recyclerShape: StateFlow<RecyclerShape>
        get() = mRecyclerShape
    val mRecyclerShape = MutableStateFlow<RecyclerShape>(RecyclerShape.Grid)
    private val products: LiveData<List<Product>>
        get() = _products
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val sortedProducts: LiveData<List<Product>>
        get() = _sortedProducts
    private val _sortedProducts: MutableLiveData<List<Product>> = MutableLiveData()
    private fun getProductsList() {
        viewModelScope.launch(Dispatchers.Main) {
            _mainUiState.value = MainUiState.Loading

            when (val response = repository.getProducts()) {
                is Resource.Success -> {
                    val productsList = response.data?.data!!
                    _products.postValue(productsList)
                    _sortedProducts.postValue(productsList)

                    _mainUiState.value = MainUiState.Success
                }
                is Resource.Error -> {
                    _mainUiState.value = MainUiState.Error(response.message!!)
                }
            }

        }
    }

    sealed class RecyclerShape {
        object Grid : RecyclerShape()
        object Vertical : RecyclerShape()

    }

    sealed class MainUiState {
        object Empty : MainUiState()
        object Success : MainUiState()
        data class Error(val error: String) : MainUiState()
        object Loading : MainUiState()
    }

    sealed class Sort {
        object MostRecent : Sort()
        object PriceHighToLow : Sort()
        object PriceLowToHigh : Sort()
        object NameAToZ : Sort()
    }

    fun sortList(sort: Sort) {
        when (sort) {
            is Sort.MostRecent -> {
                _sortedProducts.postValue(products.value!!)
                _sortText.postValue("sort By : Most Recent ")
            }
            is Sort.PriceHighToLow -> {
                _sortedProducts.postValue(products.value!!.sortedByDescending { product -> product.price })
                _sortText.postValue("sort By : Price : High To Low")
            }
            is Sort.PriceLowToHigh -> {
                _sortedProducts.postValue(products.value!!.sortedByDescending { product -> product.price }
                    .reversed())
                _sortText.postValue("sort By : Low To High ")
            }
            is Sort.NameAToZ -> {
                _sortedProducts.postValue(products.value!!.sortedBy { product -> product.name })
                _sortText.postValue("sort By : Name : A To Z")
            }
        }
    }

    val sortText: LiveData<String>
        get() = _sortText
    private val _sortText: MutableLiveData<String> = MutableLiveData()


    init {
        getProductsList()
        _sortText.postValue("sort By : Most Recent ")
    }


    private fun signOut(view: View) {
        SharedPreferencesManager.signOutShared(sharedPreferences.edit())
        val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
        view.findNavController().navigate(action)
    }

    fun signOutIconClick(view: View) {
        alertDialog(
            "sign Out", "Are you sure you want to sign out ?",
            view.context, ::signOut, view
        )
    }


    fun addProduct(view: View) {
        val action = MainFragmentDirections.actionMainFragmentToAddItemFragment()
        view.findNavController().navigate(action)
    }

}




package com.elmorshdi.trainingtask.view.ui.mainpage

import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.Constant
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.helper.alertDialog
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository) : ViewModel() {

    private val products: LiveData<List<Product>>
        get() = _products
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val sortedProducts: LiveData<List<Product>>
        get() = _sortedProducts
    private val _sortedProducts: MutableLiveData<List<Product>> = MutableLiveData()


    val recyclerVisibility: LiveData<Boolean>
        get() = _recyclerVisibility
    private val _recyclerVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val state: LiveData<Boolean>
        get() = _state
    private val _state: MutableLiveData<Boolean> = MutableLiveData()
    val errorState: LiveData<Boolean>
        get() = _errorState
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val sortText: LiveData<String>
        get() = _sortText
    private val _sortText: MutableLiveData<String> = MutableLiveData()


    init {
        _sortText.postValue("sort By : Most Recent ")
        getProductList()
        _recyclerVisibility.postValue(false)
    }
    fun recyclerVisibility(checked: Boolean) {
        _recyclerVisibility.postValue(checked)
    }
    private fun signOut(view: View) {
        SharedPreferencesManager.signOutShared(sharedPreferences)
        val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
        view.findNavController().navigate(action)
    }

    fun signOutIconClick(view: View) {

        alertDialog(
            "sign Out", "Are you sure you want to sign out ?",
            view.context, ::signOut, view
        )
    }

    private fun getProductList() {
        _errorState.postValue(false)
        _state.postValue(false)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                try {
                    val response = repository.getProducts()
                    when (response.code()) {
                        200 -> {

                            _products.postValue(response.body()?.data!!)
                            _sortedProducts.postValue(response.body()?.data!!)

                         }
                        else -> {
                            _errorState.postValue(true)

                            _errorMessage.postValue(response.message().toString())
                        }
                    }
                } catch (e: Exception) {
                    _errorMessage.postValue(e.toString())
                    _errorState.postValue(true)

                }
                _state.postValue(true)

            }
        }
    }

    fun addProduct(view: View) {
        val action = MainFragmentDirections.actionMainFragmentToAddItemFragment()
        view.findNavController().navigate(action)
    }

    fun sortBy(i: Int) {
        when (i) {
            0 -> {
                _sortedProducts.postValue(products.value)
                _sortText.postValue("sort By : Most Recent ")

            }
            2 -> {
                _sortedProducts.postValue(sortedProducts.value?.sortedByDescending { product -> product.price })
                _sortText.postValue("sort By : Price : High To Low")

            }
            1 -> {
                _sortText.postValue("sort By : Low To High ")
                _sortedProducts.postValue(sortedProducts.value?.sortedByDescending { product ->
                    product.price
                }
                    ?.reversed())
            }
            3 -> {
                _sortText.postValue("sort By : Name : A To Z")
                _sortedProducts.postValue(sortedProducts.value?.sortedBy { product -> product.name })

            }
        }

    }



}




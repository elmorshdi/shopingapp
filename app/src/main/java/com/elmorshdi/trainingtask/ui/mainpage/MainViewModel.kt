package com.elmorshdi.trainingtask.ui.mainpage

import androidx.lifecycle.*
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.network.ApiService
import com.elmorshdi.trainingtask.network.apiService
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    val productList: LiveData<List<Product>>
        get() = _productList
    private val _productList: MutableLiveData<List<Product>> = MutableLiveData()


    init {
        getProductList()
    }

    private fun getProductList() {
        viewModelScope.launch {
            val response = apiService.getProducts()
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    _productList.postValue(response.body()?.data)

                }
            }
        }
    }
}




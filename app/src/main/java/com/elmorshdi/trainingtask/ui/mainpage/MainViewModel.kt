package com.elmorshdi.trainingtask.ui.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.network.apiService
import com.elmorshdi.trainingtask.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val stateFlow: StateFlow<List<Product>>
        get() = _stateFlow
    private val _stateFlow: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    val state: LiveData<Boolean>
        get() = _state
    private val _state: MutableLiveData<Boolean> = MutableLiveData()
    val stateCodeMessage: LiveData<String>
        get() = _stateCodeMessage
    private val _stateCodeMessage: MutableLiveData<String> = MutableLiveData()

    init {
        getProductList()
    }

    private fun getProductList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                try {
                    val repository= Repository()
                    val response = repository.getProducts()
                    when (response.code()) {
                        200 -> {
                            _state.postValue(true)
                            _stateFlow.emit(response.body()?.data!!)
                        }
                        else -> {
                            _state.postValue(false)
                            _stateCodeMessage.postValue(response.message().toString())
                        }
                    }
                } catch (e: Exception) {
                    _state.postValue(false)
                    _stateCodeMessage.postValue(e.toString())

                }

            }
        }
    }


}




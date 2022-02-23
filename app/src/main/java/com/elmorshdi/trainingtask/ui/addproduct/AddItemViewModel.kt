package com.elmorshdi.trainingtask.ui.addproduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.network.apiService
import com.elmorshdi.trainingtask.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddItemViewModel : ViewModel() {
    val addState: LiveData<Boolean>
        get() = _addState
    private val _addState: MutableLiveData<Boolean> = MutableLiveData()
    val stateCodeMessage: LiveData<String>
        get() = _stateCodeMessage
    private val _stateCodeMessage: MutableLiveData<String> = MutableLiveData()


    fun postProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val repository=Repository()
                    val response = repository.addProducts(product)
                    when (response.code()) {
                        200 -> {
                            _addState.postValue(true)
                        }
                        else -> {
                            _addState.postValue(false)
                            _stateCodeMessage.postValue(response.message().toString())
                        }
                    }
                } catch (e: Exception) {
                    _addState.postValue(false)
                    _stateCodeMessage.postValue(e.toString())
                }


            }
        }
    }
}




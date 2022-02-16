package com.elmorshdi.trainingtask.ui.addproduct

import android.util.Log
import androidx.lifecycle.*
import com.elmorshdi.trainingtask.model.Product
import com.elmorshdi.trainingtask.network.apiService
import kotlinx.coroutines.*

class AddItemViewModel : ViewModel() {
    val addState:LiveData<Boolean>
        get() = _addState
    private val _addState: MutableLiveData<Boolean> = MutableLiveData()
    val stateCode:LiveData<String>
        get() = _stateCode
    private val _stateCode: MutableLiveData<String> = MutableLiveData()




     fun postProduct(product: Product) {
          viewModelScope.launch {
            val response = apiService.addProducts(product)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                   _addState.postValue(response.body()?.status )


                }else {
                    _addState.postValue(false)
                    _stateCode.postValue(response.code().toString())

                }

            }
        }
    }
}




package com.elmorshdi.trainingtask.ui.productview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.network.apiService
import com.elmorshdi.trainingtask.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewViewModel : ViewModel() {
    val deleteState: LiveData<Boolean>
        get() = _deleteState
    private val _deleteState: MutableLiveData<Boolean> = MutableLiveData()
    val stateCode: LiveData<String>
        get() = _stateCode
    private val _stateCode: MutableLiveData<String> = MutableLiveData()

    fun delete(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                try {
                    val repository= Repository()
                    val response = repository.deleteProduct(id)
                    when (response.code()) {
                        200 -> {
                            _deleteState.postValue(true)
                        }
                        else -> {
                            _deleteState.postValue(false)
                            _stateCode.postValue(response.message().toString())
                        }
                    }
                } catch (e: Exception) {
                    _deleteState.postValue(false)
                    _stateCode.postValue(e.toString())
                }
            }
        }
    }
}

package com.elmorshdi.trainingtask.view.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.view.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
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

                    when (val response = repository.addProducts(product)) {
                        is Resource.Success -> {
                            _addState.postValue(true)
                        }
                        is Resource.Error-> {
                            _addState.postValue(false)
                            _stateCodeMessage.postValue(response.message!!)
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




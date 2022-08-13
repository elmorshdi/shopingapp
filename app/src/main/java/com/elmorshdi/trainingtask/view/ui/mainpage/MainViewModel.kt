package com.elmorshdi.trainingtask.view.ui.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.datasource.repository.MainRepository
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.view.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val mainUiState: StateFlow<MainUiState>
        get() = _mainUiState
    private val _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Empty)


     val products: LiveData<List<Product>>
        get() = _products
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()

    fun getProductsList(): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            _mainUiState.value = MainUiState.Loading

            when (val response = repository.getProducts()) {
                is Resource.Success -> {
                    val productsList = response.data?.data!!
                    _products.postValue(productsList)

                    _mainUiState.value = MainUiState.Success
                }
                is Resource.Error -> {
                    _mainUiState.value = MainUiState.Error(response.message!!)
                }
            }

        }
    }


    sealed class MainUiState {
        object Empty : MainUiState()
        object Success : MainUiState()
        data class Error(val error: String) : MainUiState()
        object Loading : MainUiState()
    }
}




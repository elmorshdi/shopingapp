package com.elmorshdi.trainingtask.view.ui.productview

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.helper.alertDialog
import com.elmorshdi.trainingtask.view.ui.login.LoginViewModel
import com.elmorshdi.trainingtask.view.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val productUiState :StateFlow<ProductUiState>
        get() = _productUiState
    private val _productUiState= MutableStateFlow<ProductUiState>(ProductUiState.Empty)

    sealed class ProductUiState {
        object Success : ProductUiState()
        data class Error(val error:String) : ProductUiState()
        object Loading : ProductUiState()
        object Empty : ProductUiState()
    }




     fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _productUiState.value=ProductUiState.Loading
            when (val response = repository.deleteProduct(id)) {
                is Resource.Success-> {
                    _productUiState.value=ProductUiState.Success
                }
                is Resource.Error->{
                    _productUiState.value=ProductUiState.Error(response.message!!)
                }
            }
        }


     }
}




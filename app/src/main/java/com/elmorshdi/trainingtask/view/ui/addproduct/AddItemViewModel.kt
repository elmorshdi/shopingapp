package com.elmorshdi.trainingtask.view.ui.addproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.datasource.repository.MainRepository
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.view.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val _addItemUiState = MutableStateFlow<AddItemUiState>(AddItemUiState.Empty)
    val addItemUiState: StateFlow<AddItemUiState> = _addItemUiState

    sealed class AddItemUiState {
        object Success : AddItemUiState()
        data class Error(val error: AddItemError) : AddItemUiState()
        object Loading : AddItemUiState()
        object Empty : AddItemUiState()
    }

    sealed class AddItemError {
        data class NetworkError(val errorMessage: String) : AddItemError()
        object NameNotValid : AddItemError()
        object PriceNotValid : AddItemError()
        object QuantityNotValid : AddItemError()

    }

    fun postProduct(name: String, price: String, quantity: String) {
        when {

            name.isEmpty()||name.all { c ->c.isDigit()}-> _addItemUiState.value =
                AddItemUiState.Error(AddItemError.NameNotValid)
            price.isEmpty()||price.toInt()==0-> _addItemUiState.value =
                AddItemUiState.Error(AddItemError.PriceNotValid)
            quantity.isEmpty()||quantity.toInt()==0 ->  _addItemUiState.value =
                AddItemUiState.Error(AddItemError.QuantityNotValid)
            else -> {
                val product = Product(
                    name = name,
                    price =  price.toInt()
                   ,
                    quantity = quantity.toInt()
                )
                postProductToDb(product)


            }
        }


    }

    fun postProductToDb(product: Product):Job {
       return viewModelScope.launch(Dispatchers.IO) {
             _addItemUiState.value = AddItemUiState.Loading
            when (val response = repository.addProducts(product)) {
                is Resource.Success -> {
                    _addItemUiState.value = AddItemUiState.Success
                }
                is Resource.Error -> {

                    _addItemUiState.value = AddItemUiState.Error(AddItemError.NetworkError(response.message!!))
                }
            }


        }
    }
}





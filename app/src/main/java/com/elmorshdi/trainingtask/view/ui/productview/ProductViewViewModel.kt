package com.elmorshdi.trainingtask.view.ui.productview

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.helper.alertDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    private fun delete(id: Int,view: View) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = repository.deleteProduct(id)
                    when (response.code()) {
                        200 -> {
                            _isLoading.postValue(false)

                            navigateToMain(view )
                        }
                        else -> {
                            _isLoading.postValue(false)

                            _errorMessage.postValue(response.message().toString())
                        }
                    }
                } catch (e: Exception) {
                    _errorMessage.postValue(e.toString())
                }
            }
        }
    }
      fun navigateToMain(view: View) {
        val action = ProductViewFragmentDirections.actionProductFragmentToMainFragment()
        view.findNavController().navigate(action)
    }
    fun deleteButtonOnClickListener(button: AppCompatButton,id :Int) {
        alertDialog(
            "Delete Product",
            "Are Sure Deleting Product",
            button.context,
            ::delete, id,button
        )
    }
}

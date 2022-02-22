package com.elmorshdi.trainingtask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.network.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    val loginState: LiveData<Boolean>
        get() = _loginState
    private val _loginState: MutableLiveData<Boolean> = MutableLiveData()
    val stateCodeMessage: LiveData<String>
        get() = _stateCodeMessage
    private val _stateCodeMessage: MutableLiveData<String> = MutableLiveData()

    var token: String? = null
    var username: String? = null


    fun login(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val response = apiService.login(email, password)
                withContext(Dispatchers.IO) {
                    when (response.code()) {
                        200 -> {
                            _loginState.postValue(response.body()?.status!!)
                            token = response.body()?.token!!
                            username = response.body()!!.data?.name!!
                        }
                        else -> {
                            _loginState.postValue(false)
                            _stateCodeMessage.postValue(response.message().toString())
                        }
                    }
                }
            }
        }
    }

}
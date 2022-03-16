package com.elmorshdi.trainingtask.view.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.helper.isEmailValid
import com.elmorshdi.trainingtask.helper.isValidPassword
import com.elmorshdi.trainingtask.view.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState
    fun login(email: String, password: String) {
        when {
            !email.isEmailValid() || email.isEmpty() -> {
                _loginUiState.value = LoginUiState.Error(Error.EmailNotValid)
            }
            !password.isValidPassword() || password.isEmpty() -> {
                _loginUiState.value = LoginUiState.Error(Error.PasswordNotValid)
            }
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _loginUiState.value = LoginUiState.Loading
                    when (val response = repository.login(email, password)) {
                        is Resource.Success -> {
                            val token = response.data?.token!!
                            val name = response.data.data?.name!!
                            _loginUiState.value = LoginUiState.Success(token, name)
                        }
                        is Resource.Error -> {
                            _loginUiState.value =
                                LoginUiState.Error(Error.NetworkError(response.message!!))
                        }
                    }
                }
            }
        }
    }

    sealed class LoginUiState {
        data class Success(val token: String, val name: String) : LoginUiState()
        data class Error(val error: LoginViewModel.Error) : LoginUiState()
        object Loading : LoginUiState()
        object Empty : LoginUiState()
    }

    sealed class Error {
        data class NetworkError(val errorMessage: String) : Error()
        object PasswordNotValid : Error()
        object EmailNotValid : Error()
    }


}
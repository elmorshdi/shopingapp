package com.elmorshdi.trainingtask.view.ui.login

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.domain.repository.Repository
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository
) : ViewModel() {
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val stateCodeMessage: LiveData<String>
        get() = _stateCodeMessage
    private val _stateCodeMessage: MutableLiveData<String> = MutableLiveData()


    fun login(email: String, password: String, view: View) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try { withContext(Dispatchers.IO) {

                    val response = repository.login(email, password)
                    withContext(Dispatchers.Main) {
                        when (response.code()) {
                            200 -> {
                                SharedPreferencesManager.signInShared(
                                    sharedPreferences.edit(),
                                    response.body()?.token!!,
                                    response.body()!!.data?.name!!
                                )
                                _isLoading.postValue(false)
                                navigateToMain(view)
                            }
                            else -> {
                                _isLoading.postValue(false)
                                _stateCodeMessage.postValue(response.message().toString())
                            }
                        }
                    }

            }
            } catch (e: Exception) {
            _isLoading.postValue(false)
            _stateCodeMessage.postValue(e.toString())
            }
        }
    }

    private fun navigateToMain(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        view.findNavController().navigate(action)
    }
}
package com.elmorshdi.trainingtask.view.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.repository.FakeRepository
import com.elmorshdi.trainingtask.view.ui.addproduct.AddItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(FakeRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun getLoginUiState() {
        assertEquals(LoginViewModel.LoginUiState.Empty, viewModel.loginUiState.value)
    }

    @Test
    fun `fun Login with unValid userName `() {
        viewModel.login("44", "22Dfre@fff")
        assertEquals(
            LoginViewModel.LoginUiState.Error(LoginViewModel.Error.EmailNotValid),
            viewModel.loginUiState.value
        )
    }
    @Test
    fun `fun postProduct with unValid password`() {
        viewModel.login("dfge@gmail.com", "fd@fff")
        assertEquals(
            LoginViewModel.LoginUiState.Error(LoginViewModel.Error.PasswordNotValid),
            viewModel.loginUiState.value
        )
    }
    @Test
    fun `fun login with valid entries`() {
        viewModel.successfulLogin("dfge@gmail.com","22Dfre@fff").invokeOnCompletion {
            assertEquals(
                LoginViewModel.LoginUiState.Success("",""),
                viewModel.loginUiState.value
            )
        }

    }
}
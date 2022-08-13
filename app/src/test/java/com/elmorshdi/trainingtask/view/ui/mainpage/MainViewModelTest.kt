package com.elmorshdi.trainingtask.view.ui.mainpage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elmorshdi.trainingtask.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel(FakeRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun getMainUiState() {
        assertEquals(MainViewModel.MainUiState.Empty,viewModel.mainUiState.value)
    }

    @Test
    fun getProductsList() {
        viewModel.getProductsList().invokeOnCompletion { assertEquals(MainViewModel.MainUiState.Success,
            viewModel.mainUiState.value)  }


    }
}
package com.elmorshdi.trainingtask.view.ui.addproduct

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elmorshdi.trainingtask.domain.model.Product
import com.elmorshdi.trainingtask.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddItemViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: AddItemViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = AddItemViewModel(FakeRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAddItemUiState() {
        assertEquals(AddItemViewModel.AddItemUiState.Empty, viewModel.addItemUiState.value)
}

    @Test
    fun `fun postProduct with zero price `() {
        viewModel.postProduct("test", "0", "22")
        assertEquals(
            AddItemViewModel.AddItemUiState.Error(AddItemViewModel.AddItemError.PriceNotValid),
            viewModel.addItemUiState.value
        )
    }
    @Test
    fun `fun postProduct with unValid name `() {
        viewModel.postProduct("44", "22", "22")
        assertEquals(
            AddItemViewModel.AddItemUiState.Error(AddItemViewModel.AddItemError.NameNotValid),
            viewModel.addItemUiState.value
        )
    }
    @Test
    fun `fun postProduct with unValid quantity `() {
        viewModel.postProduct("22", "22", "0")
        assertEquals(
            AddItemViewModel.AddItemUiState.Error(AddItemViewModel.AddItemError.QuantityNotValid),
            viewModel.addItemUiState.value
        )
    }
    @Test
    fun `fun postProduct with valid entries`() {
        viewModel.postProductToDb(Product(name = "44", price =  22, quantity =  22)).invokeOnCompletion {
            assertEquals(
                AddItemViewModel.AddItemUiState.Success,
                viewModel.addItemUiState.value
            )
        }

    }
}
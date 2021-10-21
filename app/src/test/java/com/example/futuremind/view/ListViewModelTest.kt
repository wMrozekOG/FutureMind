package com.example.futuremind.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.futuremind.createItem
import com.example.futuremind.model.Item
import com.example.futuremind.repository.ItemRepository
import com.example.futuremind.repository.RequestState
import com.example.futuremind.view.error.ErrorState
import com.example.futuremind.view.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: ItemRepository

    private val savedStateHandle = SavedStateHandle()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test data loaded`() = runBlockingTest {
        val response = listOf(createItem())
        val testFlow = flow {
            emit(RequestState.Loading(emptyList()))
            delay(100)
            emit(RequestState.Success(response))
        }
        `when`(mockRepository.getItems()).thenReturn(testFlow)

        val viewModel = ListViewModel(mockRepository, savedStateHandle, dispatcher)

        viewModel.refresh()

        dispatcher.advanceTimeBy(100)
        Assert.assertEquals(response, viewModel.items.value)
    }

    @Test
    fun `test show progress`() = runBlocking {
        val testFlow = flow {
            emit(RequestState.Loading(emptyList<Item>()))
            delay(100)
            emit(RequestState.Success(emptyList<Item>()))
        }
        `when`(mockRepository.getItems()).thenReturn(testFlow)

        val viewModel = ListViewModel(mockRepository, savedStateHandle, dispatcher)

        viewModel.refresh()

        Assert.assertEquals(true, viewModel.showProgress.value)
        dispatcher.advanceTimeBy(100)
        Assert.assertEquals(false, viewModel.showProgress.value)
    }

    @Test
    fun `test error with no data`() = runBlocking {
        val testFlow = flow {
            emit(RequestState.Loading(emptyList<Item>()))
            delay(100)
            emit(RequestState.Error(ErrorState.DATA_ERROR))
        }
        `when`(mockRepository.getItems()).thenReturn(testFlow)

        val viewModel = ListViewModel(mockRepository, savedStateHandle, dispatcher)

        viewModel.refresh()

        Assert.assertEquals(ErrorState.NO_ERROR, viewModel.fullScreenErrorState.value)
        dispatcher.advanceTimeBy(100)
        Assert.assertEquals(ErrorState.DATA_ERROR, viewModel.fullScreenErrorState.value)
    }

    @Test
    fun `test error with data`() = runBlocking {
        val response = listOf(createItem())
        val testFlow = flow {
            emit(RequestState.Loading(response))
            delay(100)
            emit(RequestState.Error(ErrorState.DATA_ERROR))
        }
        `when`(mockRepository.getItems()).thenReturn(testFlow)

        val viewModel = ListViewModel(mockRepository, savedStateHandle, dispatcher)

        viewModel.refresh()

        Assert.assertEquals(ErrorState.NO_ERROR, viewModel.fullScreenErrorState.value)
        dispatcher.advanceTimeBy(100)
        Assert.assertEquals(ErrorState.NO_ERROR, viewModel.fullScreenErrorState.value)
    }
}
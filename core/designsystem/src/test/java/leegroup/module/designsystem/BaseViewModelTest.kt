package leegroup.module.designsystem

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.test.runTest
import leegroup.module.designsystem.ui.models.ErrorDialog
import leegroup.module.designsystem.ui.models.LoadingState
import leegroup.module.designsystem.ui.models.Message
import leegroup.module.designsystem.ui.viewmodel.BaseViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MockBaseViewModelTest {

    private lateinit var mockBaseViewModel: MockBaseViewModel

    @Before
    fun setUp() {
        mockBaseViewModel = MockBaseViewModel()
    }

    @Test
    fun `test handleAction ShowLoading triggers showLoading`() = runTest {
        // Call the ShowLoading action
        mockBaseViewModel.handleAction(MockBaseViewModel.Action.ShowLoading)

        // Verify that loading state is set to Loading
        assertTrue(mockBaseViewModel.assertIsLoading())
    }

    @Test
    fun `test inject loading`() = runTest {
        // Call the ShowLoading action
        mockBaseViewModel.testInjectLoading()
            .onCompletion {
                assertEquals(LoadingState.None, mockBaseViewModel.loading.value)
            }
            .collect {
                assertTrue(mockBaseViewModel.loading.value is LoadingState.Loading)
                assertEquals(1, it)
            }

    }

    @Test
    fun `test handleAction HideLoading triggers hideLoading`() = runTest {
        // First, set loading state to show
        mockBaseViewModel.handleAction(MockBaseViewModel.Action.ShowLoading)
        assertTrue(mockBaseViewModel.loading.value is LoadingState.Loading)

        // Now, hide loading
        mockBaseViewModel.handleAction(MockBaseViewModel.Action.HideLoading)

        // Verify that the loading state is reset to None
        assertEquals(LoadingState.None, mockBaseViewModel.loading.value)
    }

    @Test
    fun `test handleAction SendMessage emits message`() = runTest {
        val msg = "Hello world"
        val testMessage = Message.SnackBarMessage.buildSuccess(alternativeMessage = msg)
        mockBaseViewModel.message.test {
            mockBaseViewModel.handleAction(MockBaseViewModel.Action.SendMessage(testMessage))
            val item = awaitItem()
            assertEquals(msg, (item as Message.SnackBarMessage).alternativeMessage)
        }
    }
}

private class MockBaseViewModel : BaseViewModel() {

    suspend fun handleAction(action: Action) {
        when (action) {
            is Action.ShowLoading -> showLoading()
            is Action.HideLoading -> hideLoading()
            is Action.HandleError -> handleErrorAndShowDialog(action.throwable)
            is Action.OnErrorConfirmation -> onErrorConfirmation(ErrorDialog.None)
            is Action.SendMessage -> sendMessage(action.message)
        }
    }

    fun testInjectLoading() = flow {
        delay(100)
        emit(1)
    }.injectLoading()

    fun assertIsLoading() = isLoading()

    sealed interface Action {
        data object ShowLoading : Action
        data object HideLoading : Action
        data class HandleError(val throwable: Throwable) : Action
        data object OnErrorConfirmation : Action
        data class SendMessage(val message: Message) : Action
    }
}

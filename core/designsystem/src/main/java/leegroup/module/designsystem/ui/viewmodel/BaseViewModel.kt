package leegroup.module.designsystem.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import leegroup.module.designsystem.support.extensions.mapToErrorDialog
import leegroup.module.designsystem.support.extensions.mapToMessage
import leegroup.module.designsystem.ui.models.ErrorDialog
import leegroup.module.designsystem.ui.models.LoadingState
import leegroup.module.designsystem.ui.models.Message

@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel() {

    private val _loading: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
    val loading = _loading.asStateFlow()

    protected val _error = MutableSharedFlow<ErrorDialog>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = _error.asSharedFlow()

    protected val _message = MutableSharedFlow<Message>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val message = _message.asSharedFlow()

    protected val _navigator = MutableSharedFlow<Any>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val navigator = _navigator.asSharedFlow()

    protected open fun showLoading() {
        _loading.value = LoadingState.Loading()
    }

    protected open fun isLoading(): Boolean {
        return _loading.value is LoadingState.Loading
    }

    protected open fun hideLoading() {
        _loading.value = LoadingState.None
    }

    protected open fun sendErrorState(
        errorState: ErrorDialog
    ) {
        _error.tryEmit(errorState)
    }

    protected open suspend fun handleErrorAndShowDialog(e: Throwable) {
        _error.tryEmit(e.mapToErrorDialog())
    }

    protected open suspend fun handleErrorAndSendMessage(e: Throwable) {
        _message.tryEmit(e.mapToMessage())
    }

    open fun onErrorConfirmation(errorState: ErrorDialog) {}

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            _message.emit(message)
        }
    }

    protected fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
}

fun BaseViewModel.sendSuccessMessage(
    messageStringId: Int? = null,
    alternativeMessage: String? = null
) {
    sendMessage(Message.SnackBarMessage.buildSuccess(messageStringId, alternativeMessage))
}


fun BaseViewModel.sendErrorMessage(
    messageStringId: Int? = null,
    alternativeMessage: String? = null
) {
    sendMessage(Message.SnackBarMessage.buildError(messageStringId, alternativeMessage))
}

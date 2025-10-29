package leegroup.module.designsystem.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import leegroup.module.core.extensions.compose.collectAsEffect
import leegroup.module.designsystem.components.ErrorView
import leegroup.module.designsystem.components.LoadingView
import leegroup.module.designsystem.ui.models.ErrorDialog
import leegroup.module.designsystem.ui.models.LocalTopSnackbarHostStateManager
import leegroup.module.designsystem.ui.models.Message
import leegroup.module.designsystem.ui.viewmodel.BaseViewModel

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    loadingView: @Composable () -> Unit = { LoadingView(viewModel) },
    messageObserver: @Composable () -> Unit = { MessageObserver(viewModel) },
    content: @Composable () -> Unit,
) {
    content()
    loadingView()
    messageObserver()
    ErrorView(viewModel)
}

@Composable
fun LoadingView(viewModel: BaseViewModel) {
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    LoadingView(loading = loading)
}


@Composable
fun ErrorView(viewModel: BaseViewModel) {
    var error: ErrorDialog? by remember { mutableStateOf(null) }
    viewModel.error.collectAsEffect { errorEvent ->
        error = errorEvent
    }
    ErrorView(
        error = error,
        onErrorConfirmation = { viewModel.onErrorConfirmation(it) },
        onErrorDismissRequest = { error = null }
    )
}

@Composable
fun MessageObserver(
    viewModel: BaseViewModel
) {
    val context = LocalContext.current
    val snackbarStateManager = LocalTopSnackbarHostStateManager.current

    val coroutineScope = rememberCoroutineScope()
    var snackbarJob: Job? = null

    viewModel.message.collectAsEffect { event ->
        when (event) {
            is Message.SnackBarMessage -> {
                snackbarJob?.cancel()
                snackbarJob = coroutineScope.launch {
                    val message = event.getMessage(context)
                    snackbarStateManager.getHostState(event.type).showSnackbar(message)
                }
            }
        }
    }
}

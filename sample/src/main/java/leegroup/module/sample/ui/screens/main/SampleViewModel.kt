package leegroup.module.sample.ui.screens.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import leegroup.module.core.util.DispatchersProvider
import leegroup.module.designsystem.ui.viewmodel.StateViewModel
import leegroup.module.sample.data.models.SampleModel
import leegroup.module.sample.domain.usecases.SampleUseCase
import leegroup.module.sample.ui.models.SampleUiState
import javax.inject.Inject

@HiltViewModel
internal class SampleViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val useCase: SampleUseCase
) : StateViewModel<SampleUiState>(SampleUiState()) {

    init {
        loadSample()
    }

    private fun loadSample() {
        useCase.invoke()
            .injectLoading()
            .onEach { sample -> handleSample(sample) }
            .flowOn(dispatchersProvider.io)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun handleSample(sampleModel: SampleModel) {
        update {
            it.updateSample(sampleModel)
        }
    }
}
package leegroup.module.sample.ui.models

import androidx.compose.runtime.Immutable
import leegroup.module.sample.data.models.SampleModel

@Immutable
internal data class SampleUiState(
    val id: Int = 0
) {

    fun updateSample(sampleModel: SampleModel): SampleUiState {
        return copy(id = sampleModel.id)
    }
}

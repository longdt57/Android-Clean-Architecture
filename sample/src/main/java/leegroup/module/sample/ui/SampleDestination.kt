package leegroup.module.sample.ui

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

sealed interface SampleDestination {
    @Serializable
    @Keep
    data object SampleScreen : SampleDestination
}

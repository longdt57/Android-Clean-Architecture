package leegroup.module.photosample.ui.screens.main

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import leegroup.module.photosample.ui.models.PhotoUiModel

sealed interface PhotoDestination {

    @Serializable
    @Keep
    data object PhotoList : PhotoDestination
}

internal typealias PhotoDetailNav = PhotoUiModel

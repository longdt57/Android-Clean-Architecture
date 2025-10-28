package com.app.androidcompose.ui

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

sealed class AppDestination {

    @Serializable
    @Keep
    data object RootNavGraph

    @Serializable
    @Keep
    data object MainScreen

}

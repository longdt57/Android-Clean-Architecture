package leegroup.module.compose.support.util

import androidx.lifecycle.SavedStateHandle

interface SavedStateProvider<T> {

    fun toRoute(savedStateHandle: SavedStateHandle): T
}
package leegroup.module.designsystem.support.util

import androidx.lifecycle.SavedStateHandle

interface SavedStateProvider<T> {

    fun toRoute(savedStateHandle: SavedStateHandle): T
}
package leegroup.module.sample.gituser.ui.screens

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

sealed interface GitUserDestination {

    @Serializable
    @Keep
    data object GitUserRoot : GitUserDestination

    @Serializable
    object GitUserList : GitUserDestination

    @Serializable
    data class GitUserDetail(
        val login: String
    ) : GitUserDestination
}

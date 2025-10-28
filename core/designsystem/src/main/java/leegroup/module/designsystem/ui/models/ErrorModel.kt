package leegroup.module.designsystem.ui.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class ErrorModel(
    @SerialName("message")
    val message: String? = null,
    @SerialName("code")
    val code: Int? = null,
)
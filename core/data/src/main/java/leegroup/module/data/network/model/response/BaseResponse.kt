package leegroup.module.data.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,

    val data: T,

    val message: String?,

    val code: Int?,
)
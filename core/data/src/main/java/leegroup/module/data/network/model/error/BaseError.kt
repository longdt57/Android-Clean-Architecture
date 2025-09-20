package leegroup.module.data.network.model.error

interface BaseError {
    val code: Int? get() = null
    val message: String? get() = null
}

object GenericError : BaseError {
    override val code: Int = -103
}

object NetworkError : BaseError {
    override val code: Int = -101
}

object ServerError : BaseError {
    override val code: Int = -102
}
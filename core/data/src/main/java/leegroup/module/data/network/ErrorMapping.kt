package leegroup.module.data.network

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import leegroup.module.data.network.model.error.BaseError
import leegroup.module.data.network.model.error.ErrorModel
import leegroup.module.data.network.model.error.GenericError
import leegroup.module.data.network.model.error.NetworkError
import leegroup.module.data.network.model.error.ServerError
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

suspend fun Throwable.mapApiError(): BaseError {
    return mapApiCustomError<ErrorModel>()
}

suspend inline fun <reified Model : BaseError> Throwable.mapApiCustomError(): BaseError {
    return try {
        when (this) {
            is UnknownHostException,
            is SSLException,
            is InterruptedIOException -> NetworkError

            is ConnectException -> ServerError
            is ClientRequestException -> response.body<Model>()
            else -> GenericError
        }
    } catch (e: Exception) {
        GenericError
    }
}


package leegroup.module.designsystem.support.extensions

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import leegroup.module.core.util.JsonUtil
import leegroup.module.designsystem.R
import leegroup.module.designsystem.ui.models.ErrorDialog
import leegroup.module.designsystem.ui.models.ErrorModel
import leegroup.module.designsystem.ui.models.Message
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

suspend fun Throwable.mapToErrorDialog(): ErrorDialog {
    return when (this) {
        is UnknownHostException,
        is SSLException,
        is InterruptedIOException -> ErrorDialog.Network

        is ConnectException -> ErrorDialog.Server
        is HttpException, is ClientRequestException -> ErrorDialog.Api(asErrorModel())
        else -> ErrorDialog.Common
    }
}

private val errorMapping = mapOf<Int, Int>(
    // TODO add error code here
)

@Suppress("MagicNumber")
internal suspend fun Throwable.mapToMessage(): Message {
    return when (this) {
        is ErrorModel -> {
            Message.SnackBarMessage.buildError(
                messageStringId = errorMapping[code],
                alternativeMessage = message
            )
        }

        is ClientRequestException -> {
            val error = response.body<ErrorModel>()
            Message.SnackBarMessage.buildError(
                messageStringId = errorMapping[error.code],
                alternativeMessage = message
            )
        }

        is UnknownHostException,
        is SSLException,
        is InterruptedIOException -> {
            Message.SnackBarMessage.buildError(
                messageStringId = R.string.you_are_currently_offline,
                alternativeMessage = message
            )
        }

        else -> {
            Message.SnackBarMessage.buildError(
                messageStringId = R.string.something_went_wrong,
                alternativeMessage = message
            )
        }
    }
}

internal suspend fun Throwable.asErrorModel(): ErrorModel? {
    return when (this) {
        is HttpException -> JsonUtil.decodeFromString<ErrorModel>(
            response()?.errorBody()?.string().orEmpty()
        )

        is ClientRequestException -> response.body<ErrorModel>()
        else -> null
    }
}

package leegroup.module.designsystem.ui.models

import android.content.Context

interface Message {

    class SnackBarMessage(
        val messageStringId: Int? = null,
        val alternativeMessage: String? = null,
        val type: SnackbarType,
    ) : Message {

        fun getMessage(context: Context): String {
            return alternativeMessage.takeUnless { it.isNullOrBlank() } ?: messageStringId?.let {
                context.getString(it)
            }.orEmpty()
        }

        companion object {
            fun buildSuccess(msgId: Int? = null, alternativeMessage: String? = null) =
                SnackBarMessage(msgId, alternativeMessage, SnackbarType.Success)

            fun buildWarning(msgId: Int? = null, alternativeMessage: String? = null) =
                SnackBarMessage(msgId, alternativeMessage, SnackbarType.Warning)

            fun buildError(messageStringId: Int? = null, alternativeMessage: String? = null) =
                SnackBarMessage(messageStringId, alternativeMessage, SnackbarType.Error)
        }
    }

}

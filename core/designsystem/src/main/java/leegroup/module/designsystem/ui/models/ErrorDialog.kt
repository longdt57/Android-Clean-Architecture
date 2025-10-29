package leegroup.module.designsystem.ui.models

import leegroup.module.designsystem.R

interface ErrorDialog {
    data object None : ErrorDialog

    interface MessageError : ErrorDialog {
        val iconRes: Int? get() = null
        val titleRes: Int get() = R.string.something_went_wrong
        val messageRes: Int get() = R.string.popup_error_unknown_body
        val primaryRes: Int get() = R.string.close
        val secondaryRes: Int? get() = null
    }

    data object Common : MessageError

    data object Network : MessageError {
        override val titleRes: Int = R.string.you_are_currently_offline
        override val messageRes: Int = R.string.popup_error_no_connection_body
        override val primaryRes: Int = R.string.retry
        override val secondaryRes: Int = R.string.close
    }

    data class Api(
        val error: ErrorModel? = null,
    ) : MessageError {
        val customMessage get() = error?.message

        override val titleRes: Int = R.string.something_went_wrong
        override val messageRes: Int = R.string.popup_error_unknown_body
        override val primaryRes: Int = R.string.retry
        override val secondaryRes: Int = R.string.close
    }

    data object Server : MessageError {
        override val titleRes: Int = R.string.server_overloaded
        override val messageRes: Int = R.string.popup_error_timeout_body
        override val primaryRes: Int = R.string.close
    }
}

package leegroup.module.sample.gituser

import leegroup.module.designsystem.ui.models.ErrorDialog
import java.net.UnknownHostException

object MockUtil {
    val noConnectivityException: Throwable = UnknownHostException()
    val apiErrorState = ErrorDialog.Api()
}
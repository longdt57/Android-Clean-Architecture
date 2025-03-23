package leegroup.module.core.extensions

import android.content.Intent
import android.net.Uri
import android.util.Log

fun Intent.getExtraStreamUri(): Uri? = try {
    extras?.getParcelable(Intent.EXTRA_STREAM)
} catch (ex: Exception) {
    Log.e("getExtraStreamUri", ex.message.orEmpty())
    null
}

fun Intent.clearExtraStreamUri() {
    removeExtra(Intent.EXTRA_STREAM)
}

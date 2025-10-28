package leegroup.module.core.extensions

import android.content.Intent

private const val KEY_FORWARD_INTENT = "key_forward_intent"

fun Intent.getForwardIntent(): Intent? {
    return getParcelable<Intent>(KEY_FORWARD_INTENT)
}

fun Intent.putForwardIntent(
    value: Intent,
) {
    putExtra(KEY_FORWARD_INTENT, value)
}
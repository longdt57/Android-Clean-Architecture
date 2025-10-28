package leegroup.module.designsystem.support.extensions

import android.content.Context
import android.net.Uri
import coil3.imageLoader
import coil3.request.ImageRequest

fun Context.preloadImage(uri: Uri) {
    val request = ImageRequest.Builder(this)
        .data(uri)
        .build()

    imageLoader.enqueue(request)
}

package leegroup.module.designsystem.support.extensions

import androidx.activity.result.ActivityResultLauncher

fun ActivityResultLauncher<String>.launchImage() {
    launch("image/*")
}
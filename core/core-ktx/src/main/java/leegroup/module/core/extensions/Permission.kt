package leegroup.module.core.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun ComponentActivity.requestPushNotificationPermission(
    onPermissionDenied: (() -> Unit)? = null,
    onPermissionsGranted: (() -> Unit)? = null,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { grants ->
                // Check if any permissions weren't granted.
                for (grant in grants.entries) {
                    if (!grant.value) {
                        onPermissionDenied?.invoke()
                    }
                }
                // If all granted, notify if needed.
                if (onPermissionsGranted != null && grants.all { it.value }) {
                    onPermissionsGranted()
                }
            }

        val neededPermissions = listOf(Manifest.permission.POST_NOTIFICATIONS).toTypedArray()

        if (neededPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(neededPermissions)
        } else {
            onPermissionsGranted?.invoke()
        }
    } else {
        onPermissionsGranted?.invoke()
    }

}

fun Context.isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(
    this,
    permission
) == PackageManager.PERMISSION_GRANTED

fun Context.isCameraPermissionGranted() = isPermissionGranted(Manifest.permission.CAMERA)

fun Context.isAudioPermissionGranted() = isPermissionGranted(Manifest.permission.RECORD_AUDIO)

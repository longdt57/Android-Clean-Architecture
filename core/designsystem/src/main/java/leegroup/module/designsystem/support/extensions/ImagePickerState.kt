package leegroup.module.designsystem.support.extensions

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberImagePickerState(): ImagePickerState {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    return ImagePickerState(
        selectedImageUri = selectedImageUri,
        onPickImage = { launcher.launchImage() }
    )
}

data class ImagePickerState(
    val selectedImageUri: Uri?,
    val onPickImage: () -> Unit
)

package leegroup.module.designsystem.support.extensions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun Modifier.clearFocusOnTap(): Modifier {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val onFocusCleared: () -> Unit = {
        keyboardController?.hide()
        focusManager.clearFocus()
    }
    return pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                onFocusCleared()
            }
        )
    }
}

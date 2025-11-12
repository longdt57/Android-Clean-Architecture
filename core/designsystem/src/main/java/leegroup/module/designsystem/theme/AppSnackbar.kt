package leegroup.module.designsystem.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import leegroup.module.designsystem.R
import leegroup.module.designsystem.ui.models.SnackbarType

@Composable
fun AppSnackBar(
    snackbarType: SnackbarType,
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = Modifier
            .statusBarsPadding(),
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                containerColor = snackbarType.backgroundColor(),
                contentColor = snackbarType.contentColor(),
                shape = RoundedCornerShape(24.dp),
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 8.dp),
                            painter = painterResource(snackbarType.icon()),
                            contentDescription = null,
                        )
                        Text(
                            text = data.visuals.message,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun SnackbarType.backgroundColor() = when (this) {
    SnackbarType.Success -> MaterialTheme.colorScheme.onBackground
    SnackbarType.Warning -> MaterialTheme.colorScheme.onBackground
    SnackbarType.Error -> MaterialTheme.colorScheme.onBackground
}

@Composable
fun SnackbarType.contentColor() = when (this) {
    SnackbarType.Success -> Color.Green
    SnackbarType.Warning -> Color.Yellow
    SnackbarType.Error -> Color.Red
}

@Composable
fun SnackbarType.icon() = when (this) {
    SnackbarType.Success -> R.drawable.ic_check_green_18dp
    SnackbarType.Warning -> R.drawable.ic_warning_yellow_18dp
    SnackbarType.Error -> R.drawable.ic_error_red_18dp
}

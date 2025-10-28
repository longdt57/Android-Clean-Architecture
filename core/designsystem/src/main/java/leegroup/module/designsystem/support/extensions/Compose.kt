package leegroup.module.designsystem.support.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun <R : Any> R.applyWhen(
    condition: Boolean,
    block: R.() -> R,
): R = applyChoice(condition = condition, trueBlock = block, falseBlock = { this })

inline fun <R : Any> R.applyChoice(
    condition: Boolean,
    trueBlock: R.() -> R,
    falseBlock: R.() -> R,
): R {
    return if (condition) {
        trueBlock()
    } else {
        falseBlock()
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun heightByScreenHeight(fraction: Float = 1f): Dp {
    return LocalConfiguration.current.screenHeightDp.dp * fraction
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun widthByScreenWidth(fraction: Float = 1f): Dp {
    return LocalConfiguration.current.screenWidthDp.dp * fraction
}

@Composable
fun takeOrNull(take: Boolean, block: @Composable () -> Unit): (@Composable () -> Unit)? {
    return if (take) {
        { block() }
    } else {
        null
    }
}

@Composable
fun takeOrEmpty(take: Boolean, block: @Composable () -> Unit): @Composable () -> Unit {
    return if (take) {
        { block() }
    } else {
        {}
    }
}

@Composable
fun getStatusBarHeightPx(): Int {
    return WindowInsets.statusBars.getTop(LocalDensity.current)
}

@Composable
fun getStatusBarHeightDp(): Dp {
    val density = LocalDensity.current
    val heightPx = WindowInsets.statusBars.getTop(density)
    return with(density) { heightPx.toDp() }
}

@Composable
fun getNavigationBarHeightDp(): Dp {
    val insets = WindowInsets.navigationBars
    val density = LocalDensity.current
    return remember {
        with(density) { insets.getBottom(this).toDp() }
    }
}

@Composable
fun rememberDebouncedClick(
    debounceTimeMillis: Long = 1_000L,
    onClick: () -> Unit,
): () -> Unit {
    var lastClickTimeMillis: Long by remember { mutableLongStateOf(value = 0L) }
    return {
        val now = System.currentTimeMillis()
        if (now - lastClickTimeMillis >= debounceTimeMillis) {
            lastClickTimeMillis = now
            onClick()
        }
    }
}

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return with(density) { this@toPx.toPx() }
}
package leegroup.module.designsystem.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import leegroup.module.analytics.AnalyticsManager
import leegroup.module.analytics.clients.FirebaseClient

open class BaseCompositionActivity : BaseActivity() {

    @Composable
    protected fun AppCompositionLocalProvider(
        vararg values: ProvidedValue<*>,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(
            LocalAnalyticManager provides AnalyticsManager(
                listOf(FirebaseClient())
            ),
            *values
        ) {
            content()
        }
    }
}

val LocalAnalyticManager = staticCompositionLocalOf<AnalyticsManager> {
    error("AnalyticsManager is not present")
}

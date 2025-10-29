package com.app.androidcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.app.androidcompose.ui.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint
import leegroup.module.designsystem.theme.ComposeTheme
import leegroup.module.designsystem.ui.screen.BaseCompositionActivity

@AndroidEntryPoint
class MainActivity : BaseCompositionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompositionLocalProvider {
                ComposeTheme {
                    AppNavGraph(navController = rememberNavController())
                }
            }
        }
    }
}

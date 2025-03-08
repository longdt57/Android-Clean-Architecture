package leegroup.module.compose.support.util

interface DelayProvider {
    suspend fun delay(timeMillis: Long)
}
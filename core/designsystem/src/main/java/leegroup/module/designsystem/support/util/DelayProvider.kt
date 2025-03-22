package leegroup.module.designsystem.support.util

interface DelayProvider {
    suspend fun delay(timeMillis: Long)
}
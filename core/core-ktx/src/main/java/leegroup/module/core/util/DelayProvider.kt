package leegroup.module.core.util

interface DelayProvider {
    suspend fun delay(timeMillis: Long)
}
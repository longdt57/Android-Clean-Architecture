package leegroup.module.compose.support.util

object DelayProviderImpl : DelayProvider {
    override suspend fun delay(timeMillis: Long) {
        delay(timeMillis)
    }
}
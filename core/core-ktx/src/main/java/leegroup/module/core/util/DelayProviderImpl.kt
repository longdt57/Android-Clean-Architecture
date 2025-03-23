package leegroup.module.core.util

object DelayProviderImpl : DelayProvider {
    override suspend fun delay(timeMillis: Long) {
        delay(timeMillis)
    }
}
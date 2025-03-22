package leegroup.module.designsystem.support.util

object DelayProviderImpl : DelayProvider {
    override suspend fun delay(timeMillis: Long) {
        delay(timeMillis)
    }
}
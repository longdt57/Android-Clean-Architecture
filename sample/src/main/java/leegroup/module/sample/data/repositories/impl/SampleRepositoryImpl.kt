package leegroup.module.sample.data.repositories.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import leegroup.module.sample.data.models.SampleModel
import leegroup.module.sample.data.repositories.SampleRepository
import javax.inject.Inject

internal class SampleRepositoryImpl @Inject constructor() : SampleRepository {
    override fun getSample(): Flow<SampleModel> {
        return flowOf(SampleModel(1))
    }
}

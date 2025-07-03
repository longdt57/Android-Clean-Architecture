package leegroup.module.sample.data.repositories

import kotlinx.coroutines.flow.Flow
import leegroup.module.sample.data.models.SampleModel

internal interface SampleRepository {

    fun getSample(): Flow<SampleModel>

}
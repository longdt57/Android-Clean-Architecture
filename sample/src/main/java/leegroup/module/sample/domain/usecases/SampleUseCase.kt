package leegroup.module.sample.domain.usecases

import leegroup.module.sample.data.repositories.SampleRepository
import javax.inject.Inject

internal class SampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {

    operator fun invoke() = sampleRepository.getSample()
}
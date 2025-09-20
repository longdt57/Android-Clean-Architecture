package leegroup.module.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import leegroup.module.data.network.model.Result
import leegroup.module.data.network.model.error.BaseError
import leegroup.module.data.network.model.response.BaseResponse

fun <T> flowTransform(call: suspend FlowCollector<T>.() -> T) = flow {
    runCatching { call() }
        .onSuccess { result -> emit(result) }
        .onFailure { exception -> throw exception }
}

fun <T : Any?> safeApiCall(call: suspend () -> BaseResponse<T>): Flow<T> = flow {
    runCatching { call() }
        .onSuccess { result -> emit(result.data) }
        .onFailure { exception -> throw exception }
}

fun <T> Flow<T>.asResult(): Flow<Result<T, BaseError>> {
    return this
        .map<T, Result<T, BaseError>> { Result.Success(it) }
        .catch { e -> emit(Result.Error(e.mapApiError())) }
}

inline fun <T, reified E : BaseError> Flow<T>.asCustomResult(): Flow<Result<T, BaseError>> {
    return this
        .map<T, Result<T, BaseError>> { Result.Success(it) }
        .catch { e -> emit(Result.Error(e.mapApiCustomError<E>())) }
}
package leegroup.module.data

import app.cash.turbine.test
import io.ktor.client.request.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import leegroup.module.core.util.JsonUtil
import leegroup.module.data.network.asCustomResult
import leegroup.module.data.network.asResult
import leegroup.module.data.network.flowTransform
import leegroup.module.data.network.model.Result
import leegroup.module.data.network.model.error.ErrorModel
import leegroup.module.data.network.model.error.GenericError
import leegroup.module.data.network.model.error.SampleCustomErrorModel
import leegroup.module.data.network.model.response.BaseResponse
import leegroup.module.data.network.safeApiCall
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResponseMappingTest {

    @Test
    fun `safeApiCall emits success data`() = runTest {
        val response = BaseResponse(
            success = true,
            data = "Hello World",
            message = null,
            code = 200
        )
        val flow = safeApiCall { response }

        flow.test {
            val item = awaitItem()
            assertEquals("Hello World", item)
            awaitComplete()
        }
    }

    @Test
    fun `safeApiCall emits error when exception thrown`() = runTest {
        val flow = safeApiCall<String> { throw RuntimeException("Boom") }
            .asResult()

        flow.test {
            val item = awaitItem()
            assert(item is Result.Error)
            assertEquals(GenericError, (item as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `asResult emits Success on normal flow`() = runTest {
        val flow = flowOf("Data").asResult()

        flow.test {
            val item = awaitItem()
            assert(item is Result.Success)
            assertEquals("Data", (item as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `asResult emits Error on exception`() = runTest {
        val flow = flowTransform<String> { throw RuntimeException("Boom") }.asResult()

        flow.test {
            val item = awaitItem()
            assert(item is Result.Error)
            assertEquals(GenericError, (item as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `test 400 error with code`() = runTest {
        val expectedError = ErrorModel(
            code = 101,
            message = "Hello Bro!"
        )

        val client =
            ApiMockUtil.mockApiError(JsonUtil.encodeToString(expectedError)) // use kotlinx.serialization

        flowTransform<Unit> {
            client.get("https://fake.api/test")
        }.asResult().test {
            val item = awaitItem()
            assert(item is Result.Error)
            assertEquals(expectedError, (item as Result.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `test 400 error with custom code`() = runTest {
        val expectedError = SampleCustomErrorModel(
            code = 101,
            message = "Hello Bro!",
            title = "Hello World!"
        )

        val client =
            ApiMockUtil.mockApiError(JsonUtil.encodeToString(expectedError)) // use kotlinx.serialization

        flowTransform<Unit> {
            client.get("https://fake.api/test")
        }.asCustomResult<Unit, SampleCustomErrorModel>().test {
            val item = awaitItem()
            assert(item is Result.Error)
            assertEquals(expectedError, (item as Result.Error).error)
            awaitComplete()
        }
    }
}
package leegroup.module.data

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import kotlinx.coroutines.test.runTest
import leegroup.module.core.util.JsonUtil
import leegroup.module.data.network.mapApiCustomError
import leegroup.module.data.network.mapApiError
import leegroup.module.data.network.model.error.ErrorModel
import leegroup.module.data.network.model.error.GenericError
import leegroup.module.data.network.model.error.NetworkError
import leegroup.module.data.network.model.error.SampleCustomErrorModel
import leegroup.module.data.network.model.error.ServerError
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class ErrorMappingTest {

    @Test
    fun `UnknownHostException maps to NetworkError`() = runTest {
        val result = UnknownHostException().mapApiError()
        assertEquals(NetworkError, result)
    }

    @Test
    fun `SSLException maps to NetworkError`() = runTest {
        val result = SSLException("SSL error").mapApiError()
        assertEquals(NetworkError, result)
    }

    @Test
    fun `InterruptedIOException maps to NetworkError`() = runTest {
        val result = InterruptedIOException("Timeout").mapApiError()
        assertEquals(NetworkError, result)
    }

    @Test
    fun `ConnectException maps to ServerError`() = runTest {
        val result = ConnectException("Server down").mapApiError()
        assertEquals(ServerError, result)
    }

    @Test
    fun `Other exceptions map to UnknownError`() = runTest {
        val result = IllegalArgumentException("Something else").mapApiError()
        assertEquals(GenericError, result)
    }

    @Test
    fun `test 400 error with code`() = runTest {
        val expectedError = ErrorModel(
            code = 101,
            message = "Hello Bro!"
        )

        val client = ApiMockUtil.mockApiError(JsonUtil.encodeToString(expectedError))

        try {
            client.get("https://fake.api/test")
        } catch (e: ClientRequestException) {
            val error = e.mapApiError()

            assertEquals(expectedError, error)
        }
    }

    @Test
    fun `test error with error content`() = runTest {
        val client = ApiMockUtil.mockApiError(
            """
              "code": "Error", "Error",
            """
        )

        try {
            client.get("https://fake.api/test")
        } catch (e: ClientRequestException) {
            val error = e.mapApiError()
            assertEquals(error, GenericError)
        }
    }

    @Test
    fun `test 400 custom error with code`() = runTest {
        val customError = SampleCustomErrorModel(
            code = 101,
            message = "Hello Bro!",
            title = "Hello World!"
        )

        val client = ApiMockUtil.mockApiError(JsonUtil.encodeToString(customError))

        try {
            client.get("https://fake.api/test")
        } catch (e: ClientRequestException) {
            val error = e.mapApiCustomError<SampleCustomErrorModel>()

            assertEquals(customError, error)
        }
    }
}
package com.fappslab.resultbuilder.data.repository

import com.fappslab.resultbuilder.arch.test.rule.DispatcherTestRule
import com.fappslab.resultbuilder.data.service.PostalCodeService
import com.fappslab.resultbuilder.data.source.GENERIC_ERROR_MESSAGE
import com.fappslab.resultbuilder.data.source.PostalCodeDataSourceImpl
import com.fappslab.resultbuilder.domain.repository.PostalCodeRepository
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stubs.stubAddress
import java.io.FileNotFoundException
import java.net.HttpURLConnection.HTTP_OK
import javax.net.ssl.HttpsURLConnection.HTTP_BAD_REQUEST
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val SUCCESS_RESPONSE = "success_response.json"
private const val FAILURE_RESPONSE = "failure_response.json"

@ExperimentalCoroutinesApi
class PostalCodeRepositoryImplIntegrationTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val mockWebServer = MockWebServer()
    private lateinit var subject: PostalCodeRepository

    @Before
    fun setUp() {
        subject = PostalCodeRepositoryImpl(
            dataSource = PostalCodeDataSourceImpl(
                service = createServiceTest(),
                dispatcher = dispatcherRule.testDispatcher
            )
        )
    }

    @Test
    fun `getAddressSuccess Should return success response When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = stubAddress()
            val body = SUCCESS_RESPONSE.readJsonFile()
            mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(HTTP_OK))

            // When
            val result = subject.getAddress(code = "01001-000")

            // Then
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getAddressFailure Should return failure response When invoke getAddress`() {
        runTest {
            // Given
            val expectedMessage = GENERIC_ERROR_MESSAGE
            val body = FAILURE_RESPONSE.readJsonFile()
            mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(HTTP_BAD_REQUEST))

            // When
            val result = assertFailsWith {
                subject.getAddress(code = "01001-000")
            } as Throwable

            // Then
            assertEquals(expectedMessage, result.message)
        }
    }

    private fun createServiceTest(): PostalCodeService {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(PostalCodeService::class.java)
    }

    private fun String.readJsonFile(): String =
        requireNotNull(ClassLoader.getSystemResource(this)?.readText()) {
            throw FileNotFoundException("File $this not found!")
        }
}

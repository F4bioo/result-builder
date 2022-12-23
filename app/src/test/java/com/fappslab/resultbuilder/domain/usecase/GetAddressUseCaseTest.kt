package com.fappslab.resultbuilder.domain.usecase

import com.fappslab.resultbuilder.data.source.GENERIC_ERROR_MESSAGE
import com.fappslab.resultbuilder.domain.repository.PostalCodeRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import stubs.stubAddress
import utils.DispatcherTestRule
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class GetAddressUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val address = stubAddress()
    private val postalCodeRepository: PostalCodeRepository = mockk()
    private lateinit var subject: GetAddressUseCase

    @Before
    fun setUp() {
        subject = GetAddressUseCase(
            repository = postalCodeRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAddressSuccess Should return success result When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = address
            coEvery { postalCodeRepository.getAddress(any()) } returns expectedResult

            // When
            val result = subject(code = "01001-000")

            // Then
            coVerify { postalCodeRepository.getAddress(any()) }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getAddressFailure Should return error When invoke getAddress with blank param`() {
        runTest {
            // Given
            val expectedResult = EMPTY_FIELD_ERROR_MESSAGE

            // When
            val result = assertFailsWith {
                subject(code = "")
            } as IllegalArgumentException

            // Then
            assertEquals(expectedResult, result.message)
        }
    }

    @Test
    fun `getAddressFailure Should return failure result When invoke getAddress`() {
        runTest {
            // Given
            val expectedThrowable = IllegalArgumentException(GENERIC_ERROR_MESSAGE)
            coEvery {
                postalCodeRepository.getAddress(any())
            } throws expectedThrowable

            // When
            val result = assertFailsWith {
                subject(code = "01001-000")
            } as IllegalArgumentException

            // Then
            coVerify { postalCodeRepository.getAddress(any()) }
            assertEquals(expectedThrowable.message, result.message)
        }
    }

    @Test
    fun `getAddressFailure Should return failure result When invoke getAddress return blank address`() {
        runTest {
            // Given
            val expectedResult = NOT_FOUND_ERROR_MESSAGE
            coEvery { postalCodeRepository.getAddress(any()) } returns address.copy(street = "")

            // When
            val result = assertFailsWith {
                subject(code = "01001-000")
            } as IllegalArgumentException

            // Then
            coVerify { postalCodeRepository.getAddress(any()) }
            assertEquals(expectedResult, result.message)
        }
    }
}

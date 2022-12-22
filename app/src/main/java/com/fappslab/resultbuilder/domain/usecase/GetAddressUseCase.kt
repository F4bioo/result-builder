package com.fappslab.resultbuilder.domain.usecase

import com.fappslab.resultbuilder.domain.model.Address
import com.fappslab.resultbuilder.domain.repository.PostalCodeRepository

const val EMPTY_FIELD_ERROR_MESSAGE = "The \"postal code\" field, must be filled in!"
const val NOT_FOUND_ERROR_MESSAGE = "Postal Code not found!"

class GetAddressUseCase(
    private val repository: PostalCodeRepository,
) {

    suspend operator fun invoke(code: String): Address {
        require(code.isNotBlank()) { EMPTY_FIELD_ERROR_MESSAGE }
        return repository.getAddress(code).ifEmptyThrow()
    }

    private fun Address.ifEmptyThrow(): Address {
        require(listOf(street, city, state, areaCode)
            .all { it.isNotEmpty() }) { NOT_FOUND_ERROR_MESSAGE }
        return this
    }
}

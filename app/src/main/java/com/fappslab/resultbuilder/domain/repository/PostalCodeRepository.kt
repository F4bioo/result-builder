package com.fappslab.resultbuilder.domain.repository

import com.fappslab.resultbuilder.domain.model.Address

interface PostalCodeRepository {
    suspend fun getAddress(code: String): Address
}

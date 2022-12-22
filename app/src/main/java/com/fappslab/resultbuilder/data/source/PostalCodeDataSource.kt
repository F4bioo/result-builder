package com.fappslab.resultbuilder.data.source

import com.fappslab.resultbuilder.data.model.AddressResponse

interface PostalCodeDataSource {
    suspend fun getAddress(code: String): Result<AddressResponse>
}

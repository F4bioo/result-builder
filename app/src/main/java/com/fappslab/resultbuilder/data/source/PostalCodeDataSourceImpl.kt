package com.fappslab.resultbuilder.data.source

import com.fappslab.resultbuilder.data.model.AddressResponse
import com.fappslab.resultbuilder.data.service.PostalCodeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val GENERIC_ERROR_MESSAGE =
    "Some thing went wrong. Please, check Internet Connection!"

class PostalCodeDataSourceImpl(
    private val service: PostalCodeService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PostalCodeDataSource {

    override suspend fun getAddress(code: String): Result<AddressResponse> =
        withContext(dispatcher) {
            runCatching {
                service.getAddress(code)
            }.onFailure { throw IllegalArgumentException(GENERIC_ERROR_MESSAGE) }
        }
}

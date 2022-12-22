package com.fappslab.resultbuilder.data.repository

import com.fappslab.resultbuilder.data.source.PostalCodeDataSource
import com.fappslab.resultbuilder.domain.model.Address
import com.fappslab.resultbuilder.domain.repository.PostalCodeRepository

class PostalCodeRepositoryImpl(
    private val dataSource: PostalCodeDataSource,
) : PostalCodeRepository {

    override suspend fun getAddress(code: String): Address =
        dataSource.getAddress(code).mapCatching { it.toAddress() }.getOrThrow()
}

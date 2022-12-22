package com.fappslab.resultbuilder.data.service

import com.fappslab.resultbuilder.data.model.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PostalCodeService {

    @GET("ws/{code}/json")
    suspend fun getAddress(
        @Path("code") code: String,
    ): AddressResponse
}

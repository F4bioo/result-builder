package com.fappslab.resultbuilder.data.client

import com.fappslab.resultbuilder.arch.network.HttpClient
import retrofit2.Retrofit

internal class HttpClientImpl(
    private val retrofit: Retrofit,
) : HttpClient {

    override fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}

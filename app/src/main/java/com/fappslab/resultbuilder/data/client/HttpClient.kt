package com.fappslab.resultbuilder.data.client

interface HttpClient {
    fun <T> create(clazz: Class<T>): T
}

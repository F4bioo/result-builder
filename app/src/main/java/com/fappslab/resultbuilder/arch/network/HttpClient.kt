package com.fappslab.resultbuilder.arch.network

interface HttpClient {
    fun <T> create(clazz: Class<T>): T
}

package com.fappslab.resultbuilder.data.di

import com.fappslab.resultbuilder.BuildConfig
import com.fappslab.resultbuilder.data.client.HttpClient
import com.fappslab.resultbuilder.data.client.HttpClientImpl
import com.fappslab.resultbuilder.data.client.RetrofitClient
import com.fappslab.resultbuilder.data.repository.PostalCodeRepositoryImpl
import com.fappslab.resultbuilder.data.service.PostalCodeService
import com.fappslab.resultbuilder.data.source.PostalCodeDataSourceImpl
import com.fappslab.resultbuilder.domain.repository.PostalCodeRepository
import com.fappslab.resultbuilder.domain.usecase.GetAddressUseCase
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

val presentationModule: Module = module {
    viewModel {
        MainViewModel(
            getAddressUseCase = GetAddressUseCase(
                repository = getPostalCodeRepository()
            )
        )
    }
}

val dataModule: Module = module {
    single<HttpClient> {
        HttpClientImpl(
            retrofit = RetrofitClient(BuildConfig.BASE_URL).create()
        )
    }
}

private fun Scope.getPostalCodeRepository(): PostalCodeRepository =
    PostalCodeRepositoryImpl(
        dataSource = PostalCodeDataSourceImpl(
            service = get<HttpClient>().create(
                clazz = PostalCodeService::class.java
            )
        )
    )

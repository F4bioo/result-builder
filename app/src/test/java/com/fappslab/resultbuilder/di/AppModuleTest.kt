package com.fappslab.resultbuilder.di

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

class AppModuleTest : AutoCloseKoinTest() {

    @Test
    fun `checkModules Should Koin provides dependencies When invoke AppModule`() {
        startKoin {
            modules(AppModule.modules)
        }.checkModules()
    }
}

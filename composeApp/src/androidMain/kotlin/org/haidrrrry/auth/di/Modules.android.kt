package org.haidrrrry.auth.di


import org.koin.core.module.Module
import org.koin.dsl.module
import org.haidrrrry.auth.dao.DatabaseFactory
import org.koin.android.ext.koin.androidApplication

actual val platformModule: Module = module {
    single<DatabaseFactory> { DatabaseFactory(androidApplication()) }
}
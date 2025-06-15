package org.haidrrrry.auth.di



import org.koin.core.module.Module
import org.koin.dsl.module
import org.haidrrrry.auth.dao.DatabaseFactory

actual val platformModule: Module = module {
    single<DatabaseFactory> { DatabaseFactory() }
}
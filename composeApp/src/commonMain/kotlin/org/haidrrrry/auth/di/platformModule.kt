package org.haidrrrry.auth.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.haidrrrry.auth.dao.DatabaseFactory
import org.haidrrrry.auth.dao.FavoriteBookDatabase
import org.haidrrrry.auth.repository.AuthViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import org.haidrrrry.auth.repository.AuthRepository


expect val platformModule: Module

val sharedModule = module {

    // Database
    single<FavoriteBookDatabase> {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    // DAO
    single {
        get<FavoriteBookDatabase>().favoriteBookDao
    }

    // Repository
    single<AuthRepository> {
        AuthRepository(get())
    }

    // ViewModel
    viewModel<AuthViewModel> {
        AuthViewModel(get())
    }
}
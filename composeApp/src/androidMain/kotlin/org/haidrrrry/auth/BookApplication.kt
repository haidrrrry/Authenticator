package org.haidrrrry.auth

import android.app.Application
import org.haidrrrry.auth.di.initKoin

import org.koin.android.ext.koin.androidContext

class BookApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)

        }
    }
}
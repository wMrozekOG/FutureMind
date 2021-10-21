package com.example.futuremind

import android.app.Application
import com.example.futuremind.di.coroutineDispatcherModule
import com.example.futuremind.di.databaseModule
import com.example.futuremind.di.mainModule
import com.example.futuremind.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                databaseModule,
                networkModule,
                mainModule,
                coroutineDispatcherModule
            )
        }
    }
}
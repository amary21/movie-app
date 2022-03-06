package com.amarydev.movieapp

import android.app.Application
import com.amarydev.movieapp.core.di.databaseModule
import com.amarydev.movieapp.core.di.networkModule
import com.amarydev.movieapp.core.di.repositoryModule
import com.amarydev.movieapp.di.useCaseModule
import com.amarydev.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
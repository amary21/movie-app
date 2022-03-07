package com.amarydev.movieapp

import com.amarydev.core.di.databaseModule
import com.amarydev.core.di.networkModule
import com.amarydev.core.di.repositoryModule
import com.amarydev.movieapp.di.useCaseModule
import com.amarydev.movieapp.di.viewModelModule
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class App : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        SplitCompat.install(this)

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
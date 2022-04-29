package com.amarydev.movieapp.core.di

import androidx.room.Room
import com.amarydev.movieapp.domain.repository.IRepository
import com.amarydev.movieapp.core.data.Repository
import com.amarydev.movieapp.core.data.source.local.LocalDataSource
import com.amarydev.movieapp.core.data.source.local.room.ConfigDatabase
import com.amarydev.movieapp.core.data.source.remote.RemoteDataSource
import com.amarydev.movieapp.core.data.source.remote.network.ApiService
import com.amarydev.movieapp.core.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.URL_HEAD)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
val databaseModule = module {
    factory { get<ConfigDatabase>().movieDao() }
    factory { get<ConfigDatabase>().tvDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            ConfigDatabase::class.java,
            "Movie.db",
        ).fallbackToDestructiveMigration()
            .build()
    }
}
val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get()) }
    factory<IRepository> { Repository(get(), get()) }
}
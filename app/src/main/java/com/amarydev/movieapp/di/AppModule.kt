package com.amarydev.movieapp.di

import com.amarydev.movieapp.domain.usecase.Interactor
import com.amarydev.movieapp.domain.usecase.UseCase
import com.amarydev.movieapp.ui.detail.DetailViewModel
import com.amarydev.movieapp.ui.tvshow.TvShowViewModel
import com.amarydev.movieapp.ui.movie.MovieViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UseCase> { Interactor(get()) }
}

val viewModelModule = module {
    factory {
        Dispatchers.IO
    }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}
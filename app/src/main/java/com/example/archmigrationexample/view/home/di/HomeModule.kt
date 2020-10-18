package com.example.archmigrationexample.view.home.di

import com.example.archmigrationexample.view.home.ui.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val homeModule = module {

    factory {
        HomeViewModel(get(), get())
    }
}
package com.example.archmigrationexample.view.home.di

import com.example.archmigrationexample.view.home.ui.HomeViewModel
import org.koin.dsl.module

val homeModule = module {

    factory {
        HomeViewModel(get(), get())
    }
}
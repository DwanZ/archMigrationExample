package com.example.archmigrationexample.view.home.di

import com.example.archmigrationexample.view.home.ui.HomePresenter
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val homeModule = module {
    factory  {
        HomePresenter(get(), get())
    }
}
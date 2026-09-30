package com.example.archmigrationexample.view.detail.di

import com.example.archmigrationexample.view.detail.ui.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val detailModule = module {
    factory  {
        DetailViewModel(get())
    }
}
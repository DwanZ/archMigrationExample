package com.example.archmigrationexample.view.detail.di

import com.example.archmigrationexample.view.detail.ui.DetailPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val detailModule = module {
    factory  {
        DetailPresenter(get())
    }
}
package com.example.archmigrationexample.view.detail.di

import com.example.archmigrationexample.view.detail.ui.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(get()) }
}

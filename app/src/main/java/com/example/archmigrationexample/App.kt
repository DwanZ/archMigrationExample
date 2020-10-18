package com.example.archmigrationexample

import android.app.Application
import com.example.archmigrationexample.data.di.dataModule
import com.example.archmigrationexample.usecase.di.useCaseModule
import com.example.archmigrationexample.view.detail.di.detailModule
import com.example.archmigrationexample.view.home.di.homeModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataModule,
                useCaseModule, homeModule, detailModule)
        }
    }
}

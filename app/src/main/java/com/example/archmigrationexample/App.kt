package com.example.archmigrationexample

import android.app.Application
import com.example.archmigrationexample.data.dataModule
import com.example.archmigrationexample.usecase.useCaseModule
import com.example.archmigrationexample.view.home.di.homeModule
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataModule, useCaseModule, homeModule)
        }
    }
}

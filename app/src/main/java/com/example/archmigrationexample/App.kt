package com.example.archmigrationexample

import android.app.Application
import com.example.archmigrationexample.data.di.dataModule
import com.example.archmigrationexample.usecase.di.useCaseModule
import com.example.archmigrationexample.view.detail.di.detailModule
import com.example.archmigrationexample.view.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                useCaseModule,
                homeModule,
                detailModule
            )
        }
    }
}

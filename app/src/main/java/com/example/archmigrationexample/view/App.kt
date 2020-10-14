package com.example.archmigrationexample.view

import android.app.Application
import com.example.archmigrationexample.data.dataModule
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataModule)
        }
    }
}

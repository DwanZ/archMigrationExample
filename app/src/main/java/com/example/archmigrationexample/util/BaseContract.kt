package com.example.archmigrationexample.util

import androidx.lifecycle.LifecycleObserver

interface BaseContract {
    interface View<in T : Presenter> {

        fun setPresenter(presenter: T)
    }

    interface Presenter: LifecycleObserver {

        fun subscribe()

        fun unsubscribe()
    }
}
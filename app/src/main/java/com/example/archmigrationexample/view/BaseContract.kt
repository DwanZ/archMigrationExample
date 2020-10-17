package com.example.archmigrationexample.view


interface BaseContract {
    interface View<in T : Presenter> { }

    interface Presenter { }
}
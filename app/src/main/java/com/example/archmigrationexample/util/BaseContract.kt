package com.example.archmigrationexample.util


interface BaseContract {
    interface View<in T : Presenter> { }

    interface Presenter { }
}
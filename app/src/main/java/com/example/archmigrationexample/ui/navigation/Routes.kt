package com.example.archmigrationexample.ui.navigation

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{name}"

    fun detail(name: String) = "detail/$name"
}

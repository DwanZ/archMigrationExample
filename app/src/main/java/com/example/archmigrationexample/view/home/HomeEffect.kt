package com.example.archmigrationexample.view.home

sealed interface HomeEffect {
    data class NavigateToDetail(val name: String) : HomeEffect
}

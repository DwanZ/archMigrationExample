package com.example.archmigrationexample.view.home

sealed class HomeEvent {
    class NextPage(val index: Int): HomeEvent()
    class PreviousPage(val index: Int): HomeEvent()
    object RefreshPage: HomeEvent()
    object OnViewHidden: HomeEvent()
    class OpenDetail(val name: String): HomeEvent()
}

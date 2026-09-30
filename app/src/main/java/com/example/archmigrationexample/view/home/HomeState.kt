package com.example.archmigrationexample.view.home

import com.example.archmigrationexample.data.entity.PokemonListEntity

sealed class HomeState {
    object Loading: HomeState()
    class Success(val value: PokemonListEntity): HomeState()
    object EmptyList: HomeState()
    object OnViewHidden: HomeState()
    class Error(val error: Throwable): HomeState()
    class OpenDetail(val name: String): HomeState()
}

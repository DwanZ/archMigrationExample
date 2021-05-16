package com.example.archmigrationexample.view.detail.ui

import com.example.archmigrationexample.data.entity.PokemonEntity

data class DetailViewState(
    val loading: Boolean = true,
    val value: PokemonEntity? = null,
    val error: Throwable? = null
)

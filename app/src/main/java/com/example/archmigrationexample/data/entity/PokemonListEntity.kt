package com.example.archmigrationexample.data.entity

data class PokemonListEntity(
    val previous: Int?,
    val next: Int?,
    val results: List<PokemonItemListEntity>)
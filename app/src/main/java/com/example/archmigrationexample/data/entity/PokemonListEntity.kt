package com.example.archmigrationexample.data.entity

data class PokemonListEntity(
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: List<PokemonItemListEntity>)
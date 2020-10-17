package com.example.archmigrationexample.data

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Result

interface PokemonDataSource {

    suspend fun getPokemonByName(name: String): Result<PokemonEntity>

    suspend fun getPokemonsByPagination(offset: String): Result<PokemonListEntity>

}
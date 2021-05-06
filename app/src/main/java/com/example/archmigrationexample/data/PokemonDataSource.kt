package com.example.archmigrationexample.data

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.ApiResponse

interface PokemonDataSource {

    suspend fun getPokemonByName(name: String): ApiResponse<PokemonEntity>

    suspend fun getPokemonsByPagination(offset: String): ApiResponse<PokemonListEntity>

}
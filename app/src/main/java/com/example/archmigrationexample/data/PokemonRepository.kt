package com.example.archmigrationexample.data

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.data.source.remote.PokemonRemoteDataSource
import com.example.archmigrationexample.util.ApiResponse

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonDataSource {

    override suspend fun getPokemonByName(name: String): ApiResponse<PokemonEntity> {
        return pokemonRemoteDataSource.getPokemonByName(name)
    }

    override suspend fun getPokemonsByPagination(offset: String): ApiResponse<PokemonListEntity> {
        return pokemonRemoteDataSource.getPokemonsByPagination(offset)
    }

}
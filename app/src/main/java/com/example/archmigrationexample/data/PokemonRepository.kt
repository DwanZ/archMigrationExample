package com.example.archmigrationexample.data

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.data.source.remote.PokemonRemoteDataSource
import com.example.archmigrationexample.util.Result

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonDataSource {

    override suspend fun getPokemonByName(name: String): Result<PokemonEntity> {
        return pokemonRemoteDataSource.getPokemonByName(name)
    }

    override suspend fun getPokemonsByPagination(offset: String): Result<PokemonListEntity> {
        return pokemonRemoteDataSource.getPokemonsByPagination(offset)
    }

}
package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.PokemonDataSource
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Result
import org.koin.java.KoinJavaComponent.inject

class PokemonRemoteDataSource(private val api: PokemonApi): PokemonDataSource {

    override suspend fun getPokemonByName(name: String): Result<PokemonEntity> {
        return api.getPokemonByName(name)
    }

    override suspend fun getPokemonsByPagination(offset: String): Result<List<PokemonListEntity>> {
        return api.getPokemonsByPagination(offset)
    }

}
package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.PokemonDataSource
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.ApiResponse

class PokemonRemoteDataSource(private val api: PokemonApi) : PokemonDataSource {

    override suspend fun getPokemonByName(name: String): ApiResponse<PokemonEntity> =
        try {
            val apiResponse = api.getPokemonByName(name)
            ApiResponse.Success(apiResponse)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }

    override suspend fun getPokemonsByPagination(offset: String): ApiResponse<PokemonListEntity> {
        return try {
            val apiResponse = api.getPokemonsByPagination(offset)
            ApiResponse.Success(apiResponse)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}
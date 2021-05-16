package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonEntity

    @GET("pokemon/?limit=30")
    suspend fun getPokemonsByPagination(@Query("offset") offset: String): PokemonListEntity
}
package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import retrofit2.http.GET
import com.example.archmigrationexample.util.Result
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("/pokemon/{name}")
    fun getPokemonByName(@Path("name") name: String): Result<PokemonEntity>

    @GET("/pokemon?limit=20")
    fun getPokemonsByPagination(@Query("offset") offset: String):Result<List<PokemonListEntity>>
}
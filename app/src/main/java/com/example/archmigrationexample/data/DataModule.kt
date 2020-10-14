package com.example.archmigrationexample.data

import com.example.archmigrationexample.data.source.remote.PokemonApi
import com.example.archmigrationexample.data.source.remote.PokemonRemoteDataSource
import com.example.archmigrationexample.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        val api = Retrofit.Builder().baseUrl(BASE_URL).client(
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        ).build()
            .create(PokemonApi::class.java)
    }

    single {
        val pokemonRemoteDataSource = PokemonRemoteDataSource(get())
    }

    single {
        val pokemonRepository = PokemonRepository(get())
    }


}
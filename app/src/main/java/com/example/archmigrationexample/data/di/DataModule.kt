package com.example.archmigrationexample.data.di

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.data.source.remote.PokemonApi
import com.example.archmigrationexample.data.source.remote.PokemonRemoteDataSource
import com.example.archmigrationexample.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        Retrofit.Builder()
            //.addCallAdapterFactory(CoroutineCallAdapterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).client(
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        ).build()
            .create(PokemonApi::class.java)
    }

    factory  {
        PokemonRemoteDataSource(get())
    }

    factory  {
        PokemonRepository(get())
    }

}
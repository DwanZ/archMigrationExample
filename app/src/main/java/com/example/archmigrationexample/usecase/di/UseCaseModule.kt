package com.example.archmigrationexample.usecase.di


import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val useCaseModule = module {

    factory  {
        GetPokemonByNameUseCase(get())
    }

    factory  {
        GetPokemonListByPagination(get())
    }
}
package com.example.archmigrationexample.usecase


import org.koin.dsl.module

val useCaseModule = module {

    factory  {
        GetPokemonByNameUseCase(get())
    }

    factory  {
        GetPokemonListByPagination(get())
    }
}
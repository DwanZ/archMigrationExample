package com.example.archmigrationexample.usecase

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.util.BaseUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params

class GetPokemonListByPagination(private val repository: PokemonRepository): BaseUseCase<Params>() {

    override suspend fun run(params: Params) {
        repository.getPokemonsByPagination(params.offset)
    }

    class Params(val offset: String)

}


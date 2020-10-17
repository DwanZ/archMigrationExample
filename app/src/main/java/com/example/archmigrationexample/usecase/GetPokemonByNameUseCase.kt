package com.example.archmigrationexample.usecase

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params


class GetPokemonByNameUseCase(private val repository: PokemonRepository): BaseUseCase<Params>() {

    override suspend fun run(params: Params) {
        resultChannel.send(repository.getPokemonByName(params.name))
        resultChannel.close()
    }

    class Params(val name: String)
}


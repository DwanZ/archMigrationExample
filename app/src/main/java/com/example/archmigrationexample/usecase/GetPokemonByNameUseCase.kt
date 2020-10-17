package com.example.archmigrationexample.usecase

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.util.BaseUseCase
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params
import com.example.archmigrationexample.util.Result
import okhttp3.internal.wait


class GetPokemonByNameUseCase(private val repository: PokemonRepository): BaseUseCase<Params>() {

    override suspend fun run(params: Params) {
        resultChannel.send(repository.getPokemonByName(params.name))
        resultChannel.close()
    }

    class Params(val name: String)
}


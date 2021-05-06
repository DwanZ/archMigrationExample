package com.example.archmigrationexample.usecase

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll

@ExperimentalCoroutinesApi
class GetPokemonByNameUseCase(private val repository: PokemonRepository): BaseUseCase<Params, PokemonEntity>() {

    override suspend fun run(params: Params) {
        val task = startAsync {
            repository.getPokemonByName(params.name)
        }
        awaitAll(task)
        resultChannel.send(task.getCompleted())
    }

    class Params(val name: String)
}


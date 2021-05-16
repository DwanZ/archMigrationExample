package com.example.archmigrationexample.usecase

import com.example.archmigrationexample.data.PokemonRepository
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll

@ExperimentalCoroutinesApi
class GetPokemonListByPagination(private val repository: PokemonRepository) :
    BaseUseCase<Params, PokemonListEntity>() {

    override suspend fun run(params: Params) {
        val task = startAsync {
            repository.getPokemonsByPagination(params.offset)
        }
        awaitAll(task)
        resultChannel.send(task.getCompleted())
    }

    class Params(val offset: String)

}


package com.example.archmigrationexample.view.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.archmigrationexample.data.entity.Entity
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params
import com.example.archmigrationexample.util.Result
import com.example.archmigrationexample.view.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val getPokemonListByPagination: GetPokemonListByPagination
): BaseViewModel() {

    val pokemonList = MutableLiveData<PokemonListEntity>()
    val errorList = MutableLiveData<Throwable>()
    var offset: Int = 0
    override val receiveChannel: ReceiveChannel<Result<Entity>>
        get() = getPokemonListByPagination.receiveChannel

    init {
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    fun getPokemonList(increase: Int) {
        offset += increase
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    override fun resolve(result: Result<Entity>) {
        result.handleResult (::handleListSuccess, ::handleListError)
    }

    private fun handleListSuccess(data: Entity) {
        when(data) {
            is PokemonListEntity -> pokemonList.value = data
        }
    }

    private fun handleListError(error: Throwable) {
        errorList.value = error
    }

}
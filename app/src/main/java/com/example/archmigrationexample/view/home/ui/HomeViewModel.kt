package com.example.archmigrationexample.view.home.ui

import androidx.annotation.experimental.Experimental
import androidx.lifecycle.MutableLiveData
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.view.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val getPokemonListByPagination: GetPokemonListByPagination
): BaseViewModel<PokemonListEntity>() {

    val pokemonList = MutableLiveData<PokemonListEntity>()
    val errorList = MutableLiveData<Throwable>()
    var offset: Int = 0
    override val receiveChannel: ReceiveChannel<ApiResponse<PokemonListEntity>>
        get() = getPokemonListByPagination.receiveChannel

    init {
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    fun getPokemonList(increase: Int) {
        offset += increase
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    override fun resolve(apiResponse: ApiResponse<PokemonListEntity>) {
        apiResponse.handleResult (::handleListSuccess, ::handleListError)
    }

    private fun handleListSuccess(data: PokemonListEntity) {
       pokemonList.value = data
    }

    private fun handleListError(error: Throwable) {
        errorList.value = error
    }

}
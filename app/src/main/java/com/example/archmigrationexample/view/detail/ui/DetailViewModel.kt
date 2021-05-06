package com.example.archmigrationexample.view.detail.ui

import androidx.lifecycle.MutableLiveData
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.view.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel

@ExperimentalCoroutinesApi
class DetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase) :
    BaseViewModel<PokemonEntity>() {

    val pokemon = MutableLiveData<PokemonEntity>()
    val errorP = MutableLiveData<Throwable>()
    override val receiveChannel: ReceiveChannel<ApiResponse<PokemonEntity>>
        get() = getPokemonByNameUseCase.receiveChannel

    fun getPokemonByName(name: String) {
        getPokemonByNameUseCase.invoke(Params(name))
    }

    override fun resolve(apiResponse: ApiResponse<PokemonEntity>) {
        apiResponse.handleResult(::handleSuccess, ::handleError)
    }

    private fun handleSuccess(data: PokemonEntity) {
        pokemon.value = data
    }

    private fun handleError(error: Throwable) {
        errorP.value = error
    }
}
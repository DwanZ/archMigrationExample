package com.example.archmigrationexample.view.detail.ui

import androidx.lifecycle.MutableLiveData
import com.example.archmigrationexample.data.entity.Entity
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params
import com.example.archmigrationexample.util.Result
import com.example.archmigrationexample.view.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel

@ExperimentalCoroutinesApi
class DetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase): BaseViewModel() {

    val pokemon = MutableLiveData<PokemonEntity>()
    val errorP = MutableLiveData<Throwable>()
    override val receiveChannel: ReceiveChannel<Result<Entity>>
        get() = getPokemonByNameUseCase.receiveChannel

    fun getPokemonByName(name: String) {
        getPokemonByNameUseCase.invoke(Params(name))
    }

    override fun resolve(result: Result<Entity>) {
        result.handleResult(::handleSuccess, ::handleError)
    }

    private fun handleSuccess(data: Entity) {
        when(data) {
            is PokemonEntity -> pokemon.value = data
        }
    }

    private fun handleError(error: Throwable) {
        errorP.value = error
    }
}
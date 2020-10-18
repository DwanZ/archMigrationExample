package com.example.archmigrationexample.view.detail.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.util.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class DetailPresenter(private val getPokemonByNameUseCase: GetPokemonByNameUseCase) :
    DetailContract.Presenter, CoroutineScope {

    private val job = Job()
    lateinit var view: DetailContract.View

    override val receivePokemonChannel: ReceiveChannel<Result<PokemonEntity>>
        get() = getPokemonByNameUseCase.receiveChannel as ReceiveChannel<Result<PokemonEntity>>

    override fun getPokemonByName(name: String) {
        view.showLoading()
        CoroutineScope(coroutineContext).launch {
            getPokemonByNameUseCase(GetPokemonByNameUseCase.Params(name))
            receivePokemonChannel.consumeEach {
                resolve(it)
            }
        }
    }

    private fun resolve(response: Result<PokemonEntity>) {
        response.handleResult(::handleSuccess, ::handleFailure)
    }

    private fun handleSuccess(pokemon: PokemonEntity) {
        view.hideLoading()
        view.showPokemonList(pokemon)
    }

    private fun handleFailure(error: Throwable) {
        view.hideLoading()
        view.showEmptyView(error)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

}
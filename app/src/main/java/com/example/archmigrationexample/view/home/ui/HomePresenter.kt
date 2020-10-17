package com.example.archmigrationexample.view.home.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_URL
import com.example.archmigrationexample.util.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
class HomePresenter(
    private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val getPokemonListByPagination: GetPokemonListByPagination
) : HomeContract.Presenter {

    private val job = Job()
    lateinit var view: HomeContract.View
    var offset = 0

    override val receiveListChannel: ReceiveChannel<Result<PokemonListEntity>>
        get() = getPokemonListByPagination.receiveChannel as ReceiveChannel<Result<PokemonListEntity>>
    override val receivePokemonChannel: ReceiveChannel<Result<PokemonEntity>>
        get() = getPokemonByNameUseCase.receiveChannel as ReceiveChannel<Result<PokemonEntity>>

    override fun getPokemonList(increase: Int) {
        view.showLoading()
        offset += increase
        CoroutineScope(coroutineContext).launch {
            getPokemonListByPagination(GetPokemonListByPagination.Params(offset.toString()))
            receiveListChannel.consumeEach {
                resolveList(it)
            }
        }
    }

    override fun getPokemonByName(name: String) {
        CoroutineScope(coroutineContext).launch {
            getPokemonByNameUseCase(GetPokemonByNameUseCase.Params(name))
            receivePokemonChannel.consumeEach {
                resolve(it)
            }
        }
    }

    private fun resolveList(response: Result<PokemonListEntity>) {
        response.handleResult(::handleSuccessList, ::handleFailureList)
    }

    private fun resolve(response: Result<PokemonEntity>) {
        response.handleResult(::handleSuccess, ::handleFailure)
    }

    private fun handleSuccessList(list: PokemonListEntity) {
        view.hideLoading()
        view.showPokemonList(list)

    }

    private fun handleFailureList(error: Throwable) {
        view.hideLoading()
        view.showEmptyView(error)
    }

    private fun handleSuccess(list: PokemonEntity) {
        view.hideLoading()
        view.showPokemonList(
            PokemonListEntity(
                1,
                null,
                null,
                listOf(
                    PokemonItemListEntity(
                        list.name,
                        "${POKEMON_URL}${list.id}"
                    )
                )
            )
        )
    }

    private fun handleFailure(error: Throwable) {
        view.hideLoading()
        view.showEmptyView(error)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


}
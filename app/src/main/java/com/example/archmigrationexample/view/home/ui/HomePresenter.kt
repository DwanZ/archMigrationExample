package com.example.archmigrationexample.view.home.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.util.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
class HomePresenter(private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
                    private val getPokemonListByPagination: GetPokemonListByPagination) : HomeContract.Presenter {

    private val job = Job()
    lateinit var view: HomeContract.View

    override val receiveListChannel: ReceiveChannel<Result<PokemonListEntity>>
        get() = getPokemonListByPagination.receiveChannel as ReceiveChannel<Result<PokemonListEntity>>
    override val receivePokemonChannel: ReceiveChannel<Result<PokemonEntity>>
        get() = getPokemonByNameUseCase.receiveChannel as ReceiveChannel<Result<PokemonEntity>>

    private fun resolveList(response: Result<PokemonListEntity>) {
        response.handleResult(::handleSuccessList, ::handleFailureList)
    }

    override fun getPokemonList(offset: String) {
        getPokemonListByPagination(GetPokemonListByPagination.Params(offset))
    }

    override fun getPokemonByName(name: String) {
        CoroutineScope(EmptyCoroutineContext).launch {
            getPokemonByNameUseCase(GetPokemonByNameUseCase.Params(name))
        }
    }

    private fun handleSuccessList(list: PokemonListEntity) {

    }

    private fun handleFailureList(error: Throwable){

    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


}
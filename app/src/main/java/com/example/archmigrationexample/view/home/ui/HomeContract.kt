package com.example.archmigrationexample.view.home.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.BaseContract
import com.example.archmigrationexample.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

class HomeContract {

    interface View: BaseContract.View<Presenter> {
        fun showLoading()
        fun hideLoading()
        fun showPokemonList()
        fun showEmptyView()
        fun moveLeft()
        fun moveRight()
    }

    interface Presenter: BaseContract.Presenter, CoroutineScope {
        val receiveListChannel: ReceiveChannel<Result<PokemonListEntity>>
        val receivePokemonChannel: ReceiveChannel<Result<PokemonEntity>>
        fun getPokemonList(offset: String)
        fun getPokemonByName(name: String)
    }
}
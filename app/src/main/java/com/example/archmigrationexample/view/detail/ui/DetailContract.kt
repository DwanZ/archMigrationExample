package com.example.archmigrationexample.view.detail.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.util.Result
import com.example.archmigrationexample.view.BaseContract
import kotlinx.coroutines.channels.ReceiveChannel

class DetailContract {

    interface Presenter: BaseContract.Presenter {
        val receivePokemonChannel: ReceiveChannel<Result<PokemonEntity>>
        fun getPokemonByName(name: String)
    }

    interface View: BaseContract.View<Presenter> {
        fun showLoading()
        fun hideLoading()
        fun showPokemonList(pokemon: PokemonEntity)
        fun showEmptyView(error: Throwable)
    }
}
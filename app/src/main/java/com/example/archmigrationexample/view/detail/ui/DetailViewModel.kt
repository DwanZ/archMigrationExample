package com.example.archmigrationexample.view.detail.ui

import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase.Params
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.view.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow

@ExperimentalCoroutinesApi
class DetailViewModel(private val getPokemonByNameUseCase: GetPokemonByNameUseCase) :
    BaseViewModel<PokemonEntity>() {

    private val _viewState = MutableStateFlow<DetailViewState>(DetailViewState())
    val viewState: StateFlow<DetailViewState>
        get() = _viewState

    override val receiveChannel: Flow<ApiResponse<PokemonEntity>>
        get() = getPokemonByNameUseCase.receiveChannel.consumeAsFlow()

    fun getPokemonByName(name: String) {
        getPokemonByNameUseCase.invoke(Params(name))
    }

    override fun resolve(apiResponse: ApiResponse<PokemonEntity>) {
        apiResponse.handleResult(::handleSuccess, ::handleError)
    }

    private fun handleSuccess(data: PokemonEntity) {
        _viewState.value = DetailViewState(
            loading = false,
            value = data,
            error = null
        )
    }

    private fun handleError(error: Throwable) {
        _viewState.value = DetailViewState(
            loading = false,
            value = null,
            error = error
        )
    }
}
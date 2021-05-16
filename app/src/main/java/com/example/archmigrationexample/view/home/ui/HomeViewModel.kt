package com.example.archmigrationexample.view.home.ui

import androidx.lifecycle.viewModelScope
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonByNameUseCase
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.view.BaseViewModel
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPokemonByNameUseCase: GetPokemonByNameUseCase,
    private val getPokemonListByPagination: GetPokemonListByPagination
) : BaseViewModel<PokemonListEntity>() {

    private val _viewState = MutableStateFlow<HomeState>(HomeState.Loading)
    val viewState: StateFlow<HomeState>
        get() = _viewState
    override val receiveChannel: Flow<ApiResponse<PokemonListEntity>>
        get() = getPokemonListByPagination.receiveChannel.consumeAsFlow()
    var offset: Int = 0

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            getPokemonListByPagination.invoke(Params("$offset"))
        }
    }

    fun getPokemonList(increase: Int) {
        offset += increase
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    override fun resolve(apiResponse: ApiResponse<PokemonListEntity>) {
        apiResponse.handleResult(::handleListSuccess, ::handleListError)
    }

    private fun handleListSuccess(data: PokemonListEntity) {
        if (data.results.isEmpty()) {
            _viewState.value = HomeState.EmptyList
        } else {
            _viewState.value = HomeState.Success(data)
        }
    }

    private fun handleListError(error: Throwable) {
        _viewState.value = HomeState.Error(error)
    }

    fun processUIEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RefreshPage -> {
                getPokemonList(offset)
            }
            is HomeEvent.NextPage -> {
                getPokemonList(event.index)
            }
            is HomeEvent.PreviousPage -> {
                getPokemonList(event.index)
            }
            is HomeEvent.OpenDetail -> {
                _viewState.value = HomeState.OpenDetail(event.name)
            }
            is HomeEvent.OnViewHidden -> {
                _viewState.value = HomeState.OnViewHidden
            }
        }
    }

}
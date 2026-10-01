package com.example.archmigrationexample.view.home.ui

import androidx.lifecycle.viewModelScope
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.usecase.GetPokemonListByPagination.Params
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.util.Constants.Companion.limit
import com.example.archmigrationexample.view.BaseViewModel
import com.example.archmigrationexample.view.home.HomeEffect
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val getPokemonListByPagination: GetPokemonListByPagination
) : BaseViewModel<PokemonListEntity>() {

    private val _viewState = MutableStateFlow<HomeState>(HomeState.Loading)
    val viewState: StateFlow<HomeState> = _viewState

    private val _effects = Channel<HomeEffect>(Channel.BUFFERED)
    val effects: Flow<HomeEffect> = _effects.receiveAsFlow()

    override val receiveChannel: Flow<ApiResponse<PokemonListEntity>>
        get() = getPokemonListByPagination.receiveChannel.consumeAsFlow()

    var offset: Int = 0
        private set

    val pageNumber: Int
        get() = (offset / limit) + 1

    init {
        loadCurrentPage()
    }

    override fun resolve(apiResponse: ApiResponse<PokemonListEntity>) {
        apiResponse.handleResult(::handleListSuccess, ::handleListError)
    }

    fun processUIEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RefreshPage -> {
                _viewState.value = HomeState.Loading
                loadCurrentPage()
            }
            is HomeEvent.NextPage -> {
                offset = (offset + event.index).coerceAtLeast(0)
                _viewState.value = HomeState.Loading
                loadCurrentPage()
            }
            is HomeEvent.PreviousPage -> {
                offset = (offset + event.index).coerceAtLeast(0)
                _viewState.value = HomeState.Loading
                loadCurrentPage()
            }
            is HomeEvent.OpenDetail -> {
                viewModelScope.launch {
                    _effects.send(HomeEffect.NavigateToDetail(event.name))
                }
            }
        }
    }

    private fun loadCurrentPage() {
        getPokemonListByPagination.invoke(Params("$offset"))
    }

    private fun handleListSuccess(data: PokemonListEntity) {
        _viewState.value = if (data.results.isEmpty()) {
            HomeState.EmptyList
        } else {
            HomeState.Success(data)
        }
    }

    private fun handleListError(error: Throwable) {
        _viewState.value = HomeState.Error(error)
    }
}

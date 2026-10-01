package com.example.archmigrationexample.view.home.ui

import app.cash.turbine.test
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.usecase.GetPokemonListByPagination
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.util.Constants.Companion.limit
import com.example.archmigrationexample.view.home.HomeEffect
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var resultChannel: Channel<ApiResponse<PokemonListEntity>>
    private lateinit var useCase: GetPokemonListByPagination
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        resultChannel = Channel(Channel.BUFFERED)
        useCase = mockk(relaxed = true)
        every { useCase.receiveChannel } returns resultChannel
        viewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        resultChannel.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `emits success state when list arrives`() = runTest {
        viewModel.viewState.test {
            assertTrue(awaitItem() is HomeState.Loading)
            resultChannel.send(
                ApiResponse.Success(
                    PokemonListEntity(
                        count = 60,
                        next = null,
                        previous = null,
                        results = listOf(PokemonItemListEntity("bulbasaur", "url"))
                    )
                )
            )
            advanceUntilIdle()
            val success = awaitItem()
            assertTrue(success is HomeState.Success)
            assertEquals("bulbasaur", (success as HomeState.Success).value.results.first().name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `open detail emits navigation effect`() = runTest {
        viewModel.effects.test {
            viewModel.processUIEvent(HomeEvent.OpenDetail("pikachu"))
            advanceUntilIdle()
            assertEquals(HomeEffect.NavigateToDetail("pikachu"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `next page increases offset and reloads`() = runTest {
        viewModel.processUIEvent(HomeEvent.NextPage(limit))
        advanceUntilIdle()
        assertEquals(limit, viewModel.offset)
        verify(atLeast = 2) {
            useCase.invoke(match { it.offset == "0" || it.offset == "$limit" })
        }
    }
}

package com.example.archmigrationexample.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.ui.theme.PokeOrange
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_URL
import com.example.archmigrationexample.util.Constants.Companion.limit
import com.example.archmigrationexample.view.home.HomeEffect
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import com.example.archmigrationexample.view.home.ui.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    onOpenDetail: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> onOpenDetail(effect.name)
            }
        }
    }

    HomeScreen(
        state = state,
        offset = viewModel.offset,
        pageNumber = viewModel.pageNumber,
        onEvent = viewModel::processUIEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    offset: Int,
    pageNumber: Int,
    onEvent: (HomeEvent) -> Unit
) {
    val isRefreshing = state is HomeState.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex Migration") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokeOrange,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onEvent(HomeEvent.RefreshPage) },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (state) {
                is HomeState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PokeOrange)
                    }
                }
                is HomeState.Success -> {
                    HomeContent(
                        list = state.value,
                        offset = offset,
                        pageNumber = pageNumber,
                        onEvent = onEvent
                    )
                }
                is HomeState.EmptyList -> {
                    MessageCenter("No hay más Pokémon en esta página.")
                }
                is HomeState.Error -> {
                    MessageCenter(
                        "Error al cargar datos:\n${state.error.javaClass.simpleName}"
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    list: PokemonListEntity,
    offset: Int,
    pageNumber: Int,
    onEvent: (HomeEvent) -> Unit
) {
    val totalPages = ((list.count + limit - 1) / limit).coerceAtLeast(1)

    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(list.results) { index, pokemon ->
                PokemonCard(
                    pokemon = pokemon,
                    imageUrl = "$POKEMON_IMG_URL${offset + index + 1}$PNG",
                    onClick = { onEvent(HomeEvent.OpenDetail(pokemon.name)) }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onEvent(HomeEvent.PreviousPage(-limit)) },
                enabled = offset > 0
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous page")
            }
            Text(
                text = "$pageNumber / $totalPages",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = { onEvent(HomeEvent.NextPage(limit)) },
                enabled = offset + limit < list.count
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next page")
            }
        }
    }
}

@Composable
private fun PokemonCard(
    pokemon: PokemonItemListEntity,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(84.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MessageCenter(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
    }
}

package com.example.archmigrationexample.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.ui.theme.FireHeader
import com.example.archmigrationexample.ui.theme.GrassHeader
import com.example.archmigrationexample.ui.theme.NormalHeader
import com.example.archmigrationexample.ui.theme.PokeOrange
import com.example.archmigrationexample.ui.theme.PoisonHeader
import com.example.archmigrationexample.ui.theme.WaterHeader
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_DETAIL_URL
import com.example.archmigrationexample.view.detail.ui.DetailViewModel
import com.example.archmigrationexample.view.detail.ui.DetailViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRoute(
    pokemonName: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonName) {
        if (pokemonName.isNotBlank()) {
            viewModel.getPokemonByName(pokemonName)
        }
    }

    DetailScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailViewState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        state.value?.name?.replaceFirstChar { it.uppercase() }
                            ?: "Detalle"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PokeOrange
                    )
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error.javaClass.simpleName}",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
                state.value != null -> {
                    PokemonDetailContent(pokemon = state.value)
                }
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(pokemon: PokemonEntity) {
    val headerColor = headerColorFor(pokemon.types.firstOrNull()?.type?.name)
    val abilities = pokemon.abilities.joinToString(" • ") { it.ability.name }
    val moves = pokemon.moves.take(12).joinToString(" • ") { it.move.name }
    val types = pokemon.types.joinToString(" • ") { it.type.name }
    val stats = pokemon.stats.joinToString("\n") { "${it.stat.name}: ${it.base_stat}" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "$POKEMON_IMG_DETAIL_URL${pokemon.id}$PNG",
                contentDescription = pokemon.name,
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(title = pokemon.name.replaceFirstChar { it.uppercase() }) {
                Text("Height: ${pokemon.height}")
                Text("Weight: ${pokemon.weight}")
                Text("Base XP: ${pokemon.baseExperience}")
            }
            InfoCard(title = "Types") { Text(types) }
            InfoCard(title = "Abilities") { Text(abilities) }
            InfoCard(title = "Moves") { Text(moves) }
            InfoCard(title = "Stats") { Text(stats) }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

private fun headerColorFor(type: String?): Color {
    val value = type.orEmpty()
    return when {
        value.contains("water") -> WaterHeader
        value.contains("grass") || value.contains("bug") -> GrassHeader
        value.contains("poison") -> PoisonHeader
        value.contains("fire") -> FireHeader
        value.contains("normal") -> NormalHeader
        else -> FireHeader
    }
}

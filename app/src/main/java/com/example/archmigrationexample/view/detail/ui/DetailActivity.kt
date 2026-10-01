package com.example.archmigrationexample.view.detail.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.databinding.ActivityDetailBinding
import com.example.archmigrationexample.util.Constants.Companion.NAME
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_DETAIL_URL
import com.example.archmigrationexample.util.exceptions.EmptyResponseException
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { render(it) }
            }
        }

        intent?.extras?.getString(NAME)?.let {
            viewModel.getPokemonByName(it)
        } ?: showEmptyView(EmptyResponseException())
    }

    private fun render(state: DetailViewState) {
        if (state.loading) showLoading() else hideLoading()
        state.error?.let { showEmptyView(it) }
        state.value?.let { showPokemon(it) }
    }

    private fun showLoading() {
        binding.pokemonProgress.visibility = View.VISIBLE
        binding.pokemonDetailIContainer.visibility = View.GONE
        binding.errorDetailText.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.pokemonProgress.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun showPokemon(pokemon: PokemonEntity) {
        var ability = ""
        pokemon.abilities.forEach { power -> ability += power.ability.name.plus(" // ") }
        var moves = ""
        pokemon.moves.take(12).forEach { move -> moves += move.move.name.plus(" // ") }
        var types = ""
        pokemon.types.forEach { type ->
            types += type.type.name.plus(" // ")
            setHeaderColor(type.type.name)
        }
        var stats = ""
        pokemon.stats.forEach { stat -> stats += stat.stat.name.plus(": ${stat.base_stat} \r\n") }

        binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
        binding.pokemonHeight.text = "${pokemon.height}"
        binding.pokemonWeight.text = "${pokemon.weight}"
        binding.pokemonPower.text = "[ ${ability.dropLast(3)}]"
        binding.pokemonMove.text = "[ ${moves.dropLast(3)}]"
        binding.pokemonExp.text = "${pokemon.baseExperience}"
        binding.pokemonType.text = "[ ${types.dropLast(3)}]"
        binding.pokemonItems.text = "${stats.dropLast(3)}"
        Picasso.get().load("$POKEMON_IMG_DETAIL_URL${pokemon.id}$PNG").into(binding.pokemonDetailImg)
        binding.pokemonDetailIContainer.visibility = View.VISIBLE
        binding.scrollContainer.visibility = View.VISIBLE
        binding.errorDetailText.visibility = View.GONE
    }

    private fun showEmptyView(error: Throwable) {
        binding.scrollContainer.visibility = View.GONE
        binding.errorDetailText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass.canonicalName}"
            visibility = View.VISIBLE
        }
    }

    private fun setHeaderColor(type: String) {
        val colorRes = when {
            type.contains("normal") -> R.color.colorNormalHeader
            type.contains("water") -> R.color.colorWaterHeader
            type.contains("grass") || type.contains("bug") -> R.color.colorGrassHeader
            type.contains("poison") -> R.color.colorPoisonHeader
            type.contains("fire") -> R.color.colorFireHeader
            else -> return
        }
        binding.pokemonDetailHeader.setBackgroundColor(
            ContextCompat.getColor(this, colorRes)
        )
    }
}

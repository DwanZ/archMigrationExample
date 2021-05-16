package com.example.archmigrationexample.view.detail.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.util.Constants.Companion.NAME
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_DETAIL_URL
import com.example.archmigrationexample.util.exceptions.EmptyResponseException
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent.inject

class DetailActivity : AppCompatActivity() {

    private val viewModel by inject(DetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_detail)
        intent?.extras?.getString(NAME)?.let {
            showLoading()
            viewModel.getPokemonByName(it)
        } ?: run {
            showEmptyView(EmptyResponseException())
        }
        viewModel.pokemon.observe(this, {
            hideLoading()
            showPokemonList(it)
        })
        viewModel.errorP.observe(this, {
            hideLoading()
            showEmptyView(it)
        })
    }

    private fun showLoading() {
        pokemonProgress.visibility = View.VISIBLE
        pokemonDetailIContainer.visibility = View.GONE
        errorDetailText.visibility = View.GONE
    }

    private fun hideLoading() {
        pokemonProgress.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    fun showPokemonList(pokemon: PokemonEntity) {
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
        pokemonName.text = pokemon.name.capitalize()
        pokemonHeight.text = "${pokemon.height}"
        pokemonWeight.text = "${pokemon.weight}"
        pokemonPower.text = "[ ${ability.dropLast(3)}]"
        pokemonMove.text = "[ ${moves.dropLast(3)}]"
        pokemonExp.text = "${pokemon.baseExperience}"
        pokemonType.text = "[ ${types.dropLast(3)}]"
        pokemonItems.text = "${stats.dropLast(3)}"
        Picasso.get().load("$POKEMON_IMG_DETAIL_URL${pokemon.id}$PNG").into(pokemonDetailImg)
        pokemonDetailIContainer.visibility = View.VISIBLE
        scrollContainer.visibility = View.VISIBLE
        errorDetailText.visibility = View.GONE
    }

    private fun showEmptyView(error: Throwable) {
        scrollContainer.visibility = View.GONE
        errorDetailText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass.canonicalName}"
            visibility = View.VISIBLE
        }
    }

    private fun setHeaderColor(type: String) {
        when {
            type.contains("normal") -> {
                pokemonDetailHeader.setBackgroundColor(resources.getColor(R.color.colorNormalHeader))
            }
            type.contains("water") -> {
                pokemonDetailHeader.setBackgroundColor(resources.getColor(R.color.colorWaterHeader))
            }
            type.contains("grass") || type.contains("bug") -> {
                pokemonDetailHeader.setBackgroundColor(resources.getColor(R.color.colorGrassHeader))
            }
            type.contains("poison") -> {
                pokemonDetailHeader.setBackgroundColor(resources.getColor(R.color.colorPoisonHeader))
            }
            type.contains("fire") -> {
                pokemonDetailHeader.setBackgroundColor(resources.getColor(R.color.colorFireHeader))
            }
        }
    }
}
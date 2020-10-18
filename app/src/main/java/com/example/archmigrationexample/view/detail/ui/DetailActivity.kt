package com.example.archmigrationexample.view.detail.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.util.Constants.Companion.NAME
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_DETAIL_URL
import com.example.archmigrationexample.util.exceptions.EmptyResponseException
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent

@ExperimentalCoroutinesApi
class DetailActivity : AppCompatActivity(), DetailContract.View {

    private val presenter by KoinJavaComponent.inject(DetailPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_detail)
        presenter.view = this
        intent?.extras?.getString(NAME)?.let {
            presenter.getPokemonByName(it)
        } ?: run {
            showEmptyView(EmptyResponseException())
        }
    }

    override fun showLoading() {
        pokemonProgress.visibility = View.VISIBLE
        pokemonDetailIContainer.visibility = View.GONE
    }

    override fun hideLoading() {
        pokemonProgress.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun showPokemonList(pokemon: PokemonEntity) {
        var ability = ""
        pokemon.abilities.forEach { power -> ability += power.ability.name.plus(" // ") }
        var moves = ""
        pokemon.moves.forEach { move -> moves += move.move.name.plus(" // ") }
        var types = ""
        pokemon.types.forEach { type -> types += type.type.name.plus(" // ") }
        pokemonName.text = pokemon.name.capitalize()
        pokemonHeight.text = "${pokemon.height}"
        pokemonWeight.text = "${pokemon.weight}"
        pokemonPower.text = "[ ${ability.dropLast(3)} ]"
        pokemonMove.text = "[ ${moves.dropLast(3)} ]"
        pokemonExp.text = "${pokemon.baseExperience}"
        pokemonType.text = "[ ${types.dropLast(3)} ]"
        Picasso.get().load("$POKEMON_IMG_DETAIL_URL${pokemon.id}$PNG").into(pokemonDetailImg)
        pokemonDetailIContainer.visibility = View.VISIBLE
    }

    override fun showEmptyView(error: Throwable) {

    }
}
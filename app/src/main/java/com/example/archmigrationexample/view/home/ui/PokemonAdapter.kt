package com.example.archmigrationexample.view.home.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.util.Constants
import com.example.archmigrationexample.util.Constants.Companion.NAME
import com.example.archmigrationexample.util.Constants.Companion.PNG
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_BACK_SHINY_URL
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_BACK_URL
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_SHINY_URL
import com.example.archmigrationexample.util.Constants.Companion.POKEMON_IMG_URL
import com.example.archmigrationexample.view.detail.ui.DetailActivity
import com.squareup.picasso.Picasso

class PokemonAdapter(var pokemonList: List<PokemonItemListEntity>) :
    Adapter<PokemonViewHolder>() {
    var offset: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], position, offset)
    }
}

class PokemonViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.pokemon_item, parent, false
    )
) {

    private val card: CardView =  itemView.findViewById(R.id.pokemonCard)
    private val mName: TextView = itemView.findViewById(R.id.pokemonMainName)
    private val pImg: ImageView = itemView.findViewById(R.id.pokemonMainImg)
    private val shinyIcon: ImageView = itemView.findViewById(R.id.pokemonShinyIcon)
    private val flipIcon: ImageView = itemView.findViewById(R.id.pokemonFlipIcon)
    private var flipFlag = false
    private var shinyFlag = false

    fun bind(pokemon: PokemonItemListEntity, position: Int, offset: Int) {
        mName.text = pokemon.name.capitalize()
        val url = "${POKEMON_IMG_URL}${position + 1 + offset}${PNG}"
        Picasso.get().load(url).into(pImg)
        shinyIcon.setOnClickListener {
            shinyFlag = !shinyFlag
            val url =  "${handleImgState()}${position + 1 + offset}${PNG}"
            Picasso.get().load(url).into(pImg)
        }
        flipIcon.setOnClickListener {
            flipFlag = !flipFlag
            val url = "${handleImgState()}${position + 1 + offset}${PNG}"
            Picasso.get().load(url).into(pImg)
        }
        card.setOnClickListener {
            val intent =
                Intent(
                    it.context,
                    DetailActivity::class.java
                )
            intent.putExtra(NAME, pokemon.name)
            it.context.startActivity(intent)
        }
    }

    private fun handleImgState(): String {
        return if (flipFlag && shinyFlag) {
            POKEMON_IMG_BACK_SHINY_URL
        } else if (flipFlag && !shinyFlag) {
            POKEMON_IMG_SHINY_URL
        } else if (!flipFlag && shinyFlag) {
            POKEMON_IMG_BACK_URL
        } else {
            POKEMON_IMG_URL
        }
    }

}
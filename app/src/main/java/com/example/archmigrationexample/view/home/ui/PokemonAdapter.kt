package com.example.archmigrationexample.view.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.util.Constants
import com.example.archmigrationexample.util.Constants.Companion.PNG
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

    private var mName: TextView = itemView.findViewById(R.id.pokemonMainName)
    private var pImg: ImageView = itemView.findViewById(R.id.pokemonMainImg)
    private var shinyIcon: ImageView = itemView.findViewById(R.id.pokemonShinyIcon)
    private var flipIcon: ImageView = itemView.findViewById(R.id.pokemonFlipIcon)

    fun bind(pokemon: PokemonItemListEntity, position: Int, offset: Int) {
        mName.text = pokemon.name.capitalize()
        val url = "${Constants.POKEMON_IMG_URL}${position + 1 + offset}${PNG}"
        Picasso.get().load(url).into(pImg)
    }

}
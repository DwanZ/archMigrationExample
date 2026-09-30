package com.example.archmigrationexample.data.entity

import com.google.gson.annotations.SerializedName

data class PokemonEntity(
    val abilities: List<Ability>,
    @SerializedName("base_experience") val baseExperience: Int,
    val forms: List<Form>,
    @SerializedName("game_indices") val gameIndices: List<GameIndice>,
    val height: Int,
    @SerializedName("held_items") val heldItems: List<HeldItem>,
    val id: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
) {
    data class Ability(
        val ability: Ability,
        @SerializedName("is_hidden") val isHidden: Boolean,
        val slot: Int
    ) {
        data class Ability(
            val name: String,
            val url: String
        ) 
    }

    data class Form(
        val name: String,
        val url: String
    ) 

    data class GameIndice(
        @SerializedName("game_index") val gameIndex: Int,
        val version: Version
    ) {
        data class Version(
            val name: String,
            val url: String
        ) 
    }

    data class HeldItem(
        val item: Item,
        @SerializedName("version_details") val version_details: List<VersionDetail>
    ) {
        data class Item(
            val name: String,
            val url: String
        ) 

        data class VersionDetail(
            val rarity: Int,
            val version: Version
        ) {
            data class Version(
                val name: String,
                val url: String
            ) 
        }
    }

    data class Move(
        val move: Move,
        @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetail>
    ) {
        data class Move(
            val name: String,
            val url: String
        ) 

        data class VersionGroupDetail(
            @SerializedName("level_learned_at") val levelLearned_at: Int,
            @SerializedName("move_learn_method") val moveLearnMethod: MoveLearnMethod,
            @SerializedName("version_group") val versionGroup: VersionGroup
        ) {
            data class MoveLearnMethod(
                val name: String,
                val url: String
            ) 

            data class VersionGroup(
                val name: String,
                val url: String
            ) 
        }
    }

    data class Species(
        val name: String,
        val url: String
    ) 

    data class Sprites(
        val back_default: String,
        val back_female: Any,
        val back_shiny: String,
        val back_shiny_female: Any,
        val front_default: String,
        val front_female: Any,
        val front_shiny: String,
        val front_shiny_female: Any,
        val other: Other
    ) {
        data class Other(
            val dream_world: DreamWorld,
            @SerializedName("official-artwork") val official_artwork: OfficialArtwork
        ) {
            data class DreamWorld(
                val front_default: String,
                val front_female: Any
            ) 

            data class OfficialArtwork(
                val front_default: String
            ) 
        }
    }

    data class Stat(
        val base_stat: Int,
        val effort: Int,
        val stat: Stat
    ) {
        data class Stat(
            val name: String,
            val url: String
        ) 
    }

    data class Type(
        val slot: Int,
        val type: Type
    ) {
        data class Type(
            val name: String,
            val url: String
        )
    }
}

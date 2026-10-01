package com.example.archmigrationexample.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.example.archmigrationexample.data.entity.PokemonEntity

// Brand
val BrandRed = Color(0xFFD32F2F)
val BrandRedDark = Color(0xFFB71C1C)
val BrandAccent = Color(0xFFFFB300)
val ScreenBackground = Color(0xFFFAFAFA)

// Type headers (original XML palette + extended types for clean contrast)
val FireHeader = Color(0xFFFF5C35)
val WaterHeader = Color(0xFFAEA0FD)
val GrassHeader = Color(0xFF86C480)
val NormalHeader = Color(0xFFDCD8D7)
val PoisonHeader = Color(0xFF8D6BA0)
val ElectricHeader = Color(0xFFF8D030)
val IceHeader = Color(0xFF98D8D8)
val FightingHeader = Color(0xFFC03028)
val GroundHeader = Color(0xFFE0C068)
val FlyingHeader = Color(0xFFA890F0)
val PsychicHeader = Color(0xFFF85888)
val BugHeader = Color(0xFFA8B820)
val RockHeader = Color(0xFFB8A038)
val GhostHeader = Color(0xFF705898)
val DragonHeader = Color(0xFF7038F8)
val DarkHeader = Color(0xFF705848)
val SteelHeader = Color(0xFFB8B8D0)
val FairyHeader = Color(0xFFEE99AC)

@Deprecated("Use BrandRed", ReplaceWith("BrandRed"))
val PokeOrange = BrandRed

@Deprecated("Use BrandRedDark", ReplaceWith("BrandRedDark"))
val PokeRed = BrandRedDark

@Deprecated("Use BrandAccent", ReplaceWith("BrandAccent"))
val PokeAccent = BrandAccent

/**
 * Mirrors the XML [DetailActivity] behavior: walk every type and keep the last mapped color.
 * Unknown types are skipped (same as the original `else -> return`).
 */
fun headerColorForTypes(types: List<PokemonEntity.Type>): Color {
    var color: Color? = null
    types.forEach { entry ->
        colorForTypeName(entry.type.name)?.let { color = it }
    }
    return color ?: FireHeader
}

fun colorForTypeName(type: String): Color? {
    val value = type.lowercase()
    return when {
        value.contains("normal") -> NormalHeader
        value.contains("water") -> WaterHeader
        value.contains("grass") -> GrassHeader
        value.contains("bug") -> BugHeader
        value.contains("poison") -> PoisonHeader
        value.contains("fire") -> FireHeader
        value.contains("electric") -> ElectricHeader
        value.contains("ice") -> IceHeader
        value.contains("fighting") -> FightingHeader
        value.contains("ground") -> GroundHeader
        value.contains("flying") -> FlyingHeader
        value.contains("psychic") -> PsychicHeader
        value.contains("rock") -> RockHeader
        value.contains("ghost") -> GhostHeader
        value.contains("dragon") -> DragonHeader
        value.contains("dark") -> DarkHeader
        value.contains("steel") -> SteelHeader
        value.contains("fairy") -> FairyHeader
        else -> null
    }
}

fun contrastingContentColor(background: Color): Color =
    if (background.luminance() > 0.55f) Color.Black else Color.White

/** Soft page tint derived from the type header for readable contrast under cards. */
fun softSurfaceTint(header: Color): Color {
    val r = header.red * 0.12f + 0.95f * 0.88f
    val g = header.green * 0.12f + 0.95f * 0.88f
    val b = header.blue * 0.12f + 0.95f * 0.88f
    return Color(r.coerceIn(0f, 1f), g.coerceIn(0f, 1f), b.coerceIn(0f, 1f))
}

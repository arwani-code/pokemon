package com.arwani.pokemon.data.utils

import androidx.compose.ui.graphics.Color
import com.arwani.pokemon.data.source.local.entity.StatName
import com.arwani.pokemon.ui.theme.AtkColor
import com.arwani.pokemon.ui.theme.DefColor
import com.arwani.pokemon.ui.theme.HPColor
import com.arwani.pokemon.ui.theme.SpAtkColor
import com.arwani.pokemon.ui.theme.SpDefColor
import com.arwani.pokemon.ui.theme.SpdColor
import java.util.Locale


fun String.getImageUrl(): String {
    val index = this.split("/".toRegex()).dropLast(1).last()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
}

fun Int.getImageUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"
}

fun Int.getIdString(): String = String.format("#%03d", this)

fun Int.getWeightString(): String = String.format("%.1f KG", this.toFloat() / 10)

fun Int.getHeightString(): String = String.format("%.1f M", this.toFloat() / 10)

fun String.getId(): String {
    return this.split("/".toRegex()).dropLast(1).last().format("#%03d")
}


fun parseStatToColor(stat: StatName): Color {
    return when(stat.stat.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: StatName): String {
    return when(stat.stat.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}
package com.arwani.pokemon.data.utils


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
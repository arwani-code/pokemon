package com.arwani.pokemon.data.helper

import androidx.room.TypeConverter
import com.arwani.pokemon.data.source.local.entity.StatName
import com.google.gson.Gson

class PokemonDataConverters {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun stateToJSON(data: List<StatName>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJSONToState(json: String): List<StatName> {
        return Gson().fromJson(json, Array<StatName>::class.java).toList()
    }
}
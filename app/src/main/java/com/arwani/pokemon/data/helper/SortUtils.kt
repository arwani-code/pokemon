package com.arwani.pokemon.data.helper


import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    fun getSortedQuery(sortType: SortType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM pokemon ")
        when (sortType) {
            SortType.ASCENDING -> {
                simpleQuery.append("ORDER BY name ASC")
            }

            SortType.DESCENDING -> {
                simpleQuery.append("ORDER BY name DESC")
            }

            SortType.RANDOM -> {}
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}
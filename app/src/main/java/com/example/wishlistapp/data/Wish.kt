package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish_title")
    val title: String = "",
    @ColumnInfo(name = "wish_desc")
    val description: String = ""
){
    //Function to match the searched item///////////////////////
    fun doesMatchSearchQuery(query: String) : Boolean{
        val matchingCombinations = listOf(
            "$title"
        )

        return matchingCombinations.any{
            it.contains(query,ignoreCase = true)
        }
    }
    ////////////////////////////////////////////////////////////
}

object DummyWish{
    val wishList = listOf(
        Wish(id = 1L, title = "Google Intern", description = "Study DSA and build Projects"),
        Wish(id = 2L, title = "Buy a New Phone", description = "Go for Good Processor and Camera"),
        Wish(id = 3L, title = "Have Breakfast", description = "Yummy!! Dosa")
    )
}

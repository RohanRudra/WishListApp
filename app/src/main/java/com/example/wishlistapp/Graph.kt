package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

object Graph {
    lateinit var database : WishDatabase

    val wishRepository by lazy {
        //Lazy helps in initializing the variables at the first time (only one time)
        WishRepository(wishDAO = database.wishDAO())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}
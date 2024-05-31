package com.example.wishlistapp

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object AddScreen : Screen(route = "add_screen")
    object EditScreen : Screen(route = "edit_screen")

}
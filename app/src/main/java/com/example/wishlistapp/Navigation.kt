package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               viewModel: WishViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            StartView(navController,viewModel)
        }
        
        composable(route = Screen.AddScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id"){
                        type = NavType.LongType
                        defaultValue = 0L
                        nullable = false
                    }
                )
            ) {
            val _id = if(it.arguments != null) it.arguments!!.getLong("id") else 0L
            AddEditDetailView(id = _id, viewModel = viewModel, navController = navController)
        }
    }
}

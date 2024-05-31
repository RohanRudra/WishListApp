package com.example.wishlistapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
) : ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    private val _searchResults = MutableStateFlow<List<Wish>>(emptyList())
    val searchResults: StateFlow<List<Wish>> = _searchResults

    fun onWishTitleChanged(newString: String){
        wishTitleState = newString
    }

    fun onDescriptionChanged(newString: String){
        wishDescriptionState = newString
    }

    lateinit var getAllWishes : Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getAllWishes()
        }
    }


    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            //Dispatchers.IO optimizes the operation of the particular thread function
            wishRepository.addWish(wish)
        }
    }

    fun getWishById(id: Long) : Flow<Wish> {
        return wishRepository.getWishById(id)
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

    fun deleteAllWishes(){
        viewModelScope.launch {
            wishRepository.deleteAllWishes()
        }
    }

    fun searchWishes(query: String){
        viewModelScope.launch{
            wishRepository.searchWishes(query = query).collect{
                _searchResults.value = it
            }
        }
    }

}
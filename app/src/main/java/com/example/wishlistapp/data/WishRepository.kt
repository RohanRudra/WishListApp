package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(val wishDAO: WishDAO) {

    //Repository connects the DAO to the ViewModel

    suspend fun addWish(wish : Wish){
        wishDAO.addWish(wish)
    }

    fun getAllWishes() : Flow<List<Wish>> = wishDAO.getAllWishes()

    fun getWishById(id : Long) : Flow<Wish> = wishDAO.getWishById(id)

    suspend fun updateWish(wish: Wish){
        wishDAO.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish){
        wishDAO.deleteWish(wish)
    }

    suspend fun deleteAllWishes() = wishDAO.deleteAllWishes()

    fun searchWishes(query : String) : Flow<List<Wish>> {
        return wishDAO.searchWishes("%$query%")
    }


}
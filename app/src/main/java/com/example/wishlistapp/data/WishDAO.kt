package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addWish(wishEntity: Wish)

    @Query("Select * from wish_table")
    abstract fun getAllWishes(): Flow<List<Wish>>

    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)

    @Query("Select * from wish_table where id = :id")
    abstract fun getWishById(id:Long): Flow<Wish>

    @Query("Delete from wish_table")
    abstract suspend fun deleteAllWishes()

    @Query("Select * from wish_table where wish_title like :query")
    abstract fun searchWishes(query: String): Flow<List<Wish>>

}
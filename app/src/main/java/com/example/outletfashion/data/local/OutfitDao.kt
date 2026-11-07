package com.example.outletfashion.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {

    // CREATE - Adauga o tinuta noua
    @Insert
    suspend fun insertOutfit(outfit: OutfitEntity)

    // READ - Citeste toate tinutele salvate
    @Query("SELECT * FROM outfits ORDER BY id DESC")
    fun getAllOutfits(): Flow<List<OutfitEntity>>

    // UPDATE - Modifica o tinuta existenta
    @Update
    suspend fun updateOutfit(outfit: OutfitEntity)

    // DELETE - Sterge o tinuta
    @Delete
    suspend fun deleteOutfit(outfit: OutfitEntity)
}
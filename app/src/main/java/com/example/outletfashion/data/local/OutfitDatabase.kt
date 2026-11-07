package com.example.outletfashion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [OutfitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OutfitDatabase : RoomDatabase() {

    abstract fun outfitDao(): OutfitDao

    companion object {

        @Volatile
        private var INSTANCE: OutfitDatabase? = null

        fun getDatabase(context: Context): OutfitDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OutfitDatabase::class.java,
                    "outlet_galaxy_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
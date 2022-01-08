package com.lmiceli.gamedatabase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.data.entities.GamePageKey

@Database(
    entities = [
        Game::class,
        GamePageKey::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun gamePageKeyDao(): GamePageKeyDao

    companion object {
        private const val DATABASE_NAME = "games"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                // just a cache db
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}

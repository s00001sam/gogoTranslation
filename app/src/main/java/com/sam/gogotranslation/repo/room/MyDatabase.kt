package com.sam.gogotranslation.repo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sam.gogotranslation.repo.data.TranslationEntity

@Database(
    entities = [TranslationEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(BasicConverters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "gogo_translation_database"

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }
}

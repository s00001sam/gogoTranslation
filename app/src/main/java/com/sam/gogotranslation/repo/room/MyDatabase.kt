package com.sam.gogotranslation.repo.room

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//
//@Database(
//    entities = [],
//    version = 1,
//    exportSchema = true,
//)
//@TypeConverters(BasicConverters::class)
//abstract class MyDatabase : RoomDatabase() {
////    abstract fun animalDao(): AnimalDao
//
//    companion object {
//        @Volatile
//        private var instance: MyDatabase? = null
//        private val LOCK = Any()
//        private const val DATABASE_NAME = "idea_database"
//
//        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            MyDatabase::class.java,
//            DATABASE_NAME,
//        ).fallbackToDestructiveMigration().build()
//    }
//}

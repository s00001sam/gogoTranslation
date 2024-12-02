package com.sam.gogotranslation.repo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sam.gogotranslation.repo.data.TranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationDao {
    @Upsert
    fun upsertOne(entity: TranslationEntity): Long

    @Insert
    fun insertList(list: List<TranslationEntity>)

    @Update
    fun update(entity: TranslationEntity)

    @Delete
    fun deleteOne(entity: TranslationEntity)

    @Query("SELECT * FROM translation WHERE id=:id ")
    fun getOneFlowById(id: Int): Flow<TranslationEntity?>

    @Query("SELECT * FROM translation")
    fun getListFlow(): Flow<List<TranslationEntity>>
}

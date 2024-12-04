package com.sam.gogotranslation.repo.repository

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface BaseRepository {
    suspend fun generateTranslation(input: String): GenerateContentResponse
    fun getSingleTranslation(id: Int): TranslationEntity?
    fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?>
    fun getTranslationsFlow(): Flow<List<TranslationEntity>>
    fun upsertTranslation(entity: TranslationEntity): Long
    fun deleteTranslationById(id: Int): Int
    fun updateTranslationEditTime(id: Int, time: Date): Int
}

package com.sam.gogotranslation.repo.repository

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    suspend fun generateTranslation(input: String): GenerateContentResponse
    fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?>
    fun getTranslationsFlow(): Flow<List<TranslationEntity>>
    fun upsertTranslation(entity: TranslationEntity): Long
}

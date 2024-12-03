package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import kotlinx.coroutines.flow.Flow

interface BaseDataSource {
    suspend fun generateTranslation(input: String): GenerateContentResponse
    fun upsertTranslation(entity: TranslationEntity): Long
    fun getSingleTranslation(id: Int): TranslationEntity?
    fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?>
    fun getTranslationsFlow(): Flow<List<TranslationEntity>>
    fun deleteTranslationById(id: Int): Int
}

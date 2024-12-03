package com.sam.gogotranslation.repo.repository

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.datasource.BaseDataSource
import kotlinx.coroutines.flow.Flow

class TranslationRepository(
    private val localDataSource: BaseDataSource,
    private val remoteDataSource: BaseDataSource,
) : BaseRepository {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        return remoteDataSource.generateTranslation(input)
    }

    override fun getSingleTranslation(id: Int): TranslationEntity? {
        return localDataSource.getSingleTranslation(id)
    }

    override fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?> {
        return localDataSource.getSingleTranslationFlow(id)
    }

    override fun getTranslationsFlow(): Flow<List<TranslationEntity>> {
        return localDataSource.getTranslationsFlow()
    }

    override fun upsertTranslation(entity: TranslationEntity): Long {
        return localDataSource.upsertTranslation(entity)
    }

    override fun deleteTranslationById(id: Int): Int {
        return localDataSource.deleteTranslationById(id)
    }
}

package com.sam.gogotranslation.repo.repository

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.datasource.BaseDataSource

class TranslationRepository(
    private val localDataSource: BaseDataSource,
    private val remoteDataSource: BaseDataSource,
) : BaseRepository {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        return remoteDataSource.generateTranslation(input)
    }
}

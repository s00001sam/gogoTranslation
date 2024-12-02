package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val model: GenerativeModel) : BaseDataSource {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        return model.generateContent(input)
    }

    override fun upsertTranslation(entity: TranslationEntity): Long {
        TODO("Not yet implemented")
    }

    override fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?> {
        TODO("Not yet implemented")
    }

    override fun getTranslationsFlow(): Flow<List<TranslationEntity>> {
        TODO("Not yet implemented")
    }
}

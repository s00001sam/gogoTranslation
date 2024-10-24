package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse

class RemoteDataSource(private val model: GenerativeModel) : BaseDataSource {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        return model.generateContent(input)
    }
}

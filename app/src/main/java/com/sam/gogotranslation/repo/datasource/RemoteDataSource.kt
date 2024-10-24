package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content

class RemoteDataSource(private val model: GenerativeModel) : BaseDataSource {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        return model.generateContent(
            content {
                text(input)
            }
        )
    }
}

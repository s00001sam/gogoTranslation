package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.type.GenerateContentResponse

class LocalDataSource() : BaseDataSource {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        TODO("Not yet implemented")
    }
}

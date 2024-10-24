package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.type.GenerateContentResponse

interface BaseDataSource {
    suspend fun generateTranslation(input: String): GenerateContentResponse
}

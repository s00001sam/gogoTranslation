package com.sam.gogotranslation.repo.repository

import com.google.ai.client.generativeai.type.GenerateContentResponse

interface BaseRepository {
    suspend fun generateTranslation(input: String): GenerateContentResponse
}

package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.room.MyDatabase
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val db: MyDatabase) : BaseDataSource {
    override suspend fun generateTranslation(input: String): GenerateContentResponse {
        TODO("Not yet implemented")
    }

    override fun upsertTranslation(entity: TranslationEntity): Long {
        return db.translationDao().upsertOne(entity)
    }

    override fun getSingleTranslationFlow(id: Int): Flow<TranslationEntity?> {
        return db.translationDao().getOneFlowById(id)
    }

    override fun getTranslationsFlow(): Flow<List<TranslationEntity>> {
        return db.translationDao().getListFlow()
    }
}

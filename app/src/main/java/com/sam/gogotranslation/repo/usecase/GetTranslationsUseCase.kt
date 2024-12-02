package com.sam.gogotranslation.repo.usecase

import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class GetTranslationsUseCase(private val repository: BaseRepository) {

    fun getStateFlow(): Flow<List<TranslationEntity>> {
        return repository.getTranslationsFlow()
    }
}

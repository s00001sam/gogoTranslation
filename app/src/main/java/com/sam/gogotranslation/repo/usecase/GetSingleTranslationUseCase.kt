package com.sam.gogotranslation.repo.usecase

import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class GetSingleTranslationUseCase(private val repository: BaseRepository) {

    fun getStateFlow(id: Int): Flow<TranslationEntity?> {
        return repository.getSingleTranslationFlow(id)
    }
}

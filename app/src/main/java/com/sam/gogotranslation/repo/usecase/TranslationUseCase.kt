package com.sam.gogotranslation.repo.usecase

import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class TranslationUseCase(private val repository: BaseRepository) {

    fun getStateFlow(input: String): Flow<State<GenerateContentResponse>> {
        return flow {
            runCatching {
                val result = repository.generateTranslation(input)
                emit(State.Success(result))
            }.onFailure {
                emit(State.Error(it))
            }
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(State.Loading)
            }
    }
}

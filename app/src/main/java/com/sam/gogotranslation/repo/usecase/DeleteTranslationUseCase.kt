package com.sam.gogotranslation.repo.usecase

import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class DeleteTranslationUseCase(
    private val repository: BaseRepository,
) {
    fun getStateFlow(
        id: Int,
    ): Flow<State<Int>> {
        return flow {
            runCatching {
                val deleteResult = repository.deleteTranslationById(id)
                emit(State.Success(deleteResult))
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

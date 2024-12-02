package com.sam.gogotranslation.repo.usecase

import android.content.Context
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.R
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class TranslateUseCase(
    private val context: Context,
    private val repository: BaseRepository,
) {
    fun getStateFlow(
        id: Int,
        oriInput: String,
        promptName: String,
    ): Flow<State<Pair<Long, GenerateContentResponse>>> {
        return flow {
            runCatching {
                val prompt = context.getString(
                    R.string.translation_prompt,
                    promptName,
                    oriInput,
                )
                val result = repository.generateTranslation(prompt)
                val entity = TranslationEntity(
                    id = if (id <= -1) 0 else id,
                    input = oriInput,
                    output = result.text.orEmpty(),
                )
                val upsertId = repository.upsertTranslation(entity)
                val entityId = if (upsertId <= -1) id.toLong() else upsertId
                emit(State.Success(Pair(entityId, result)))
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

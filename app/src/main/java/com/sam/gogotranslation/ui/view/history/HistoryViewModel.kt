package com.sam.gogotranslation.ui.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.usecase.DeleteTranslationUseCase
import com.sam.gogotranslation.repo.usecase.GetTranslationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTranslationsUseCase: GetTranslationsUseCase,
    private val deleteTranslationUseCase: DeleteTranslationUseCase,
) : ViewModel() {

    private val _translations = MutableStateFlow(emptyList<TranslationEntity>())
    val translations: StateFlow<List<TranslationEntity>> = _translations

    private val _deleteTranslationId = MutableStateFlow<Int?>(null)
    val deleteTranslationId: StateFlow<Int?> = _deleteTranslationId

    private val _deleteResult = MutableStateFlow<State<Any>>(State.Empty)
    val deleteResult: StateFlow<State<Any>> = _deleteResult

    init {
        collectTranslationsFlow()
        collectDeleteTranslation()

    }

    private fun collectTranslationsFlow() {
        viewModelScope.launch {
            getTranslationsUseCase.getStateFlow().collectLatest {
                _translations.tryEmit(it)
            }
        }
    }

    fun deleteTranslation(id: Int) {
        _deleteTranslationId.tryEmit(id)
    }

    fun clearDeleteId() {
        _deleteTranslationId.tryEmit(null)
    }

    private fun collectDeleteTranslation() {
        viewModelScope.launch {
            deleteTranslationId.flatMapLatest { id ->
                if (id != null) {
                    deleteTranslationUseCase.getStateFlow(id)
                } else {
                    flowOf(State.Empty)
                }
            }.collectLatest { state ->
                _deleteResult.tryEmit(state)
            }
        }
    }
}

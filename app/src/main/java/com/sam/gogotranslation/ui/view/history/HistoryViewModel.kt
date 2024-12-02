package com.sam.gogotranslation.ui.view.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.usecase.GetTranslationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTranslationsUseCase: GetTranslationsUseCase,
) : ViewModel() {

    private val _translations = MutableStateFlow(emptyList<TranslationEntity>())
    val translations: StateFlow<List<TranslationEntity>> = _translations

    init {
        collectTranslationsFlow()
    }

    private fun collectTranslationsFlow() {
        viewModelScope.launch {
            getTranslationsUseCase.getStateFlow().collectLatest {
                _translations.tryEmit(it)
            }
        }
    }
}

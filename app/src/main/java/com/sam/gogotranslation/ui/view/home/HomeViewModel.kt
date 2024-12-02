package com.sam.gogotranslation.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.LanguageEntity
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.usecase.GetTranslationUseCase
import com.sam.gogotranslation.repo.usecase.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val getTranslationUseCase: GetTranslationUseCase,
) : ViewModel() {

    private val _translationId = MutableStateFlow<Int>(-1)
    val translationId: StateFlow<Int> = _translationId

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input

    private val _currTranslationEntity = MutableStateFlow<TranslationEntity?>(TranslationEntity())
    val currTranslationEntity: StateFlow<TranslationEntity?> = _currTranslationEntity

    private val _resultState =
        MutableStateFlow<State<Pair<Long, GenerateContentResponse>>>(State.Empty)
    val resultState: StateFlow<State<Pair<Long, GenerateContentResponse>>> = _resultState

    private val _inputLanguage = MutableStateFlow<LanguageEntity>(LanguageEntity.ChineseTW)
    val inputLanguage: StateFlow<LanguageEntity> = _inputLanguage

    private val _outputLanguage = MutableStateFlow<LanguageEntity>(LanguageEntity.English)
    val outputLanguage: StateFlow<LanguageEntity> = _outputLanguage

    init {
        collectEntityFlow()
        collectTranslateFlow()
    }

    fun translate(inputTxt: String) {
        if (inputTxt.isEmpty()) return
        _input.tryEmit(inputTxt)
    }

    fun clearInput() {
        _input.tryEmit("")
    }

    fun clearCurrId() {
        _translationId.tryEmit(-1)
        _currTranslationEntity.tryEmit(null)
    }

    fun switchLanguage() {
        val temp = inputLanguage.value
        _inputLanguage.tryEmit(outputLanguage.value)
        _outputLanguage.tryEmit(temp)
    }

    private fun collectEntityFlow() {
        viewModelScope.launch {
            translationId.flatMapLatest {
                getTranslationUseCase.getStateFlow(id = it)
            }.collect {
                _currTranslationEntity.tryEmit(it)
            }
        }
    }

    private fun collectTranslateFlow() {
        viewModelScope.launch {
            input.flatMapLatest {
                if (it.isNotEmpty()) {
                    translateUseCase.getStateFlow(
                        id = translationId.value,
                        oriInput = it,
                        promptName = outputLanguage.value.promptName,
                    )
                } else {
                    flowOf(State.Empty)
                }
            }.collectLatest { state ->
                if (state is State.Success) {
                    val id = state.data.first.toInt()
                    _translationId.tryEmit(id)
                }
                _resultState.tryEmit(state)
            }
        }
    }
}

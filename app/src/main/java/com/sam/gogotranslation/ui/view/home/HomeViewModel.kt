package com.sam.gogotranslation.ui.view.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.R
import com.sam.gogotranslation.repo.data.LanguageEntity
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.usecase.TranslationUseCase
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
    private val translationUseCase: TranslationUseCase,
) : ViewModel() {

    private val _input = MutableStateFlow<String>("")
    val input: StateFlow<String> = _input

    private val _resultState = MutableStateFlow<State<GenerateContentResponse>>(State.Empty)
    val resultState: StateFlow<State<GenerateContentResponse>> = _resultState

    private val _inputLanguage = MutableStateFlow<LanguageEntity>(LanguageEntity.ChineseTW)
    val inputLanguage: StateFlow<LanguageEntity> = _inputLanguage

    private val _outputLanguage = MutableStateFlow<LanguageEntity>(LanguageEntity.English)
    val outputLanguage: StateFlow<LanguageEntity> = _outputLanguage

    init {
        collectTranslationFlow()
    }

    fun translate(
        context: Context,
        inputTxt: String
    ) {
        if (inputTxt.isEmpty()) return

        val prompt = context.getString(
            R.string.translation_prompt,
            outputLanguage.value.promptName,
            inputTxt,
        )
        _input.tryEmit(prompt)
    }

    fun clearInput() {
        _input.tryEmit("")
    }

    fun switchLanguage() {
        val temp = inputLanguage.value
        _inputLanguage.tryEmit(outputLanguage.value)
        _outputLanguage.tryEmit(temp)
    }

    private fun collectTranslationFlow() {
        viewModelScope.launch {
            input.flatMapLatest {
                if (it.isNotEmpty()) {
                    translationUseCase.getStateFlow(it)
                } else {
                    flowOf(State.Empty)
                }
            }.collectLatest { state ->
                _resultState.tryEmit(state)
            }
        }
    }
}

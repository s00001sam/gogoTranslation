package com.sam.gogotranslation.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.sam.gogotranslation.repo.data.LanguageEntity
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.repo.repository.BaseRepository
import com.sam.gogotranslation.repo.usecase.GetSingleTranslationUseCase
import com.sam.gogotranslation.repo.usecase.TranslateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val translateUseCase: TranslateUseCase,
    private val getSingleTranslationUseCase: GetSingleTranslationUseCase,
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

    fun setCurrId(id: Int) {
        _translationId.tryEmit(id)
    }

    fun setInputLanguage(language: LanguageEntity) {
        _inputLanguage.tryEmit(language)
    }

    fun setOutputLanguage(language: LanguageEntity) {
        _outputLanguage.tryEmit(language)
    }

    fun switchLanguage() {
        val temp = inputLanguage.value
        setInputLanguage(outputLanguage.value)
        setOutputLanguage(temp)
    }

    suspend fun updateTranslationEditTime(
        id: Int,
        time: Date = Date(),
    ): Int {
        return withContext(Dispatchers.IO) {
            repository.updateTranslationEditTime(id, time)
        }
    }

    private fun collectEntityFlow() {
        viewModelScope.launch {
            translationId.flatMapLatest {
                getSingleTranslationUseCase.getStateFlow(id = it)
            }.collect {
                _currTranslationEntity.tryEmit(it)
                if (it == null) {
                    clearInput()
                }
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
                        inputLanguage = inputLanguage.value,
                        outputLanguage = outputLanguage.value,
                    )
                } else {
                    flowOf(State.Empty)
                }
            }.collectLatest { state ->
                _resultState.tryEmit(state)
                when (state) {
                    is State.Success -> {
                        val id = state.data.first.toInt()
                        setCurrId(id)
                    }

                    is State.Error -> {
                        clearInput()
                    }

                    else -> {}
                }
            }
        }
    }
}

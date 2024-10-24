package com.sam.gogotranslation.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _result = MutableStateFlow<String>("")
    val result: StateFlow<String> = _result

    init {
        collectTranslationFlow()
    }

    fun translate(inputTxt: String) {
        if (inputTxt.isEmpty()) return
        val content = "請將以下翻譯成簡單英文（不要說明，翻譯即可）\n$inputTxt）"
        _input.tryEmit(content)
    }

    fun clearInput() {
        _input.tryEmit("")
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
                when (state) {
                    is State.Success -> {
                        _result.value = state.data.text.orEmpty()
                    }

                    is State.Error -> {
                        _result.value = state.throwable.message.orEmpty()
                    }

                    is State.Loading -> {
                        _result.value = "Loading"
                    }

                    State.Empty -> _result.value = ""
                }
            }
        }
    }
}

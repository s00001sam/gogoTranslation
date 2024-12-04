package com.sam.gogotranslation.repo.data

import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat.ID_NULL
import com.sam.gogotranslation.R

sealed class LanguageEntity(
    @StringRes val displayNameRes: Int,
    val promptName: String,
    val languageTag: String,
) {
    data object ChineseTW : LanguageEntity(
        R.string.language_chinese_tw,
        CHINESE_PROMPT_NAME,
        CHINESE_TAG,
    )

    data object English : LanguageEntity(
        R.string.language_english,
        ENGLISH_PROMPT_NAME,
        ENGLISH_TAG,
    )

    data object Unknown : LanguageEntity(
        ID_NULL,
        LANGUAGE_UNKNOWN_PROMPT_NAME,
        LANGUAGE_UNKNOWN_TAG,
    )

    companion object {
        const val CHINESE_TAG = "zh-TW"
        const val CHINESE_PROMPT_NAME = "繁體中文"
        const val ENGLISH_TAG = "en-US"
        const val ENGLISH_PROMPT_NAME = "簡單英文"
        const val LANGUAGE_UNKNOWN_TAG = "unknown"
        const val LANGUAGE_UNKNOWN_PROMPT_NAME = "unknown"

        fun String.getLanguageEntity(): LanguageEntity {
            return when (this) {
                CHINESE_TAG -> ChineseTW
                ENGLISH_TAG -> English
                else -> Unknown
            }
        }
    }
}

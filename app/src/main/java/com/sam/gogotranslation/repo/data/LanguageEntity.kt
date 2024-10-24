package com.sam.gogotranslation.repo.data

import androidx.annotation.StringRes
import com.sam.gogotranslation.R

sealed class LanguageEntity(
    @StringRes val displayNameRes: Int,
    val promptName: String,
    val languageTag: String,
) {
    data object ChineseTW : LanguageEntity(
        R.string.language_chinese_tw,
        "繁體中文",
        "zh-TW",
    )

    data object English : LanguageEntity(
        R.string.language_english,
        "簡單英文",
        "en-US",
    )
}

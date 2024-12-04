package com.sam.gogotranslation.repo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sam.gogotranslation.repo.data.LanguageEntity.Companion.getLanguageEntity
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "translation")
@Parcelize
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val input: String = "",
    val output: String = "",
    val inputLanguageTag: String = "",
    val outputLanguageTag: String = "",
    val createTime: Date = Date(),
    val editTime: Date = Date(),
) : Parcelable {
    @get:Ignore
    val inputLanguage: LanguageEntity
        get() = inputLanguageTag.getLanguageEntity()

    @get:Ignore
    val outputLanguage: LanguageEntity
        get() = inputLanguageTag.getLanguageEntity()
}

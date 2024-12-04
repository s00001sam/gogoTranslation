package com.sam.gogotranslation.repo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sam.gogotranslation.repo.data.LanguageEntity.Companion.getLanguageEntity
import com.sam.gogotranslation.utils.toZonedDateTime
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime
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
        get() = outputLanguageTag.getLanguageEntity()

    @get:Ignore
    val createZoneDateTime: ZonedDateTime?
        get() = createTime.toZonedDateTime()

    @get:Ignore
    val editZoneDateTime: ZonedDateTime?
        get() = editTime.toZonedDateTime()
}

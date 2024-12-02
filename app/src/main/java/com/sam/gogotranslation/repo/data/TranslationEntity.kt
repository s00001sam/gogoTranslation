package com.sam.gogotranslation.repo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "translation")
@Parcelize
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val input: String = "",
    val output: String = "",
) : Parcelable

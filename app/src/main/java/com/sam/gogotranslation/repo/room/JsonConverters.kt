package com.sam.gogotranslation.repo.room

import androidx.room.TypeConverter
import com.sam.gogotranslation.repo.data.LanguageEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

class JsonConverters {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private inline fun <reified T> convertJsonToObject(json: String): T? =
        moshi.adapter(T::class.java).fromJson(json)

    private inline fun <reified T> convertObjectToJson(objectData: T?): String? =
        moshi.adapter(T::class.java).toJson(objectData)

    @TypeConverter
    fun fromString(value: String): List<String>? {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        return moshi.adapter(List::class.java).toJson(list)
    }

    @TypeConverter
    fun long2Date(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun date2Long(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun string2Language(entity: LanguageEntity?): String {
        return convertObjectToJson(entity).orEmpty()
    }

    @TypeConverter
    fun language2String(json: String): LanguageEntity? {
        return convertJsonToObject<LanguageEntity>(json)
    }
}

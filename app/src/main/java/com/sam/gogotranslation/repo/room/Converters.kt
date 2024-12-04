package com.sam.gogotranslation.repo.room

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BasicConverters {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

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
}

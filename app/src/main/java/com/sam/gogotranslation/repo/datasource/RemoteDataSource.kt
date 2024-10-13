package com.sam.gogotranslation.repo.datasource

import com.google.ai.client.generativeai.GenerativeModel

class RemoteDataSource(private val model: GenerativeModel) : BaseDataSource {
    fun test() {
        model.requestOptions
    }
}

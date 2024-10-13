package com.sam.gogotranslation.repo.repository

import com.sam.gogotranslation.repo.datasource.BaseDataSource

class TranslationRepository(
    private val localDataSource: BaseDataSource,
    private val remoteDataSource: BaseDataSource,
) : BaseRepository {
}

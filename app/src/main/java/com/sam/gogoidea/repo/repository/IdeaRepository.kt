package com.sam.gogoidea.repo.repository

import com.sam.gogoidea.repo.datasource.BaseDataSource

class IdeaRepository(
    private val localDataSource: BaseDataSource,
    private val remoteDataSource: BaseDataSource,
) : BaseRepository {
}

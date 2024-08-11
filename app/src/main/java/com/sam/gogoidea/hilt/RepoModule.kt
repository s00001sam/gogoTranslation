package com.sam.gogoidea.hilt

import com.google.ai.client.generativeai.GenerativeModel
import com.sam.gogoidea.BuildConfig
import com.sam.gogoidea.repo.datasource.BaseDataSource
import com.sam.gogoidea.repo.datasource.LocalDataSource
import com.sam.gogoidea.repo.datasource.RemoteDataSource
import com.sam.gogoidea.repo.repository.IdeaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {
    @Singleton
    @Provides
    fun providerGeminiModel(): GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_KEY
    )

    @Singleton
    @LocalData
    @Provides
    fun providerLocalDataSource(): BaseDataSource {
        return LocalDataSource()
    }

    @Singleton
    @RemoteData
    @Provides
    fun providerRemoteDataSource(
        model: GenerativeModel,
    ): BaseDataSource {
        return RemoteDataSource(model)
    }

    @Singleton
    @Provides
    fun provideRepository(
        @RemoteData remoteDataSource: BaseDataSource,
        @LocalData localDataSource: BaseDataSource,
    ) = IdeaRepository(remoteDataSource, localDataSource)
}

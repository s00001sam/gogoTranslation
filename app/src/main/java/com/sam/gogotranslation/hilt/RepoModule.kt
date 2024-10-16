package com.sam.gogotranslation.hilt

import com.google.ai.client.generativeai.GenerativeModel
import com.sam.gogotranslation.BuildConfig
import com.sam.gogotranslation.repo.datasource.BaseDataSource
import com.sam.gogotranslation.repo.datasource.LocalDataSource
import com.sam.gogotranslation.repo.datasource.RemoteDataSource
import com.sam.gogotranslation.repo.repository.TranslationRepository
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
    ) = TranslationRepository(remoteDataSource, localDataSource)
}
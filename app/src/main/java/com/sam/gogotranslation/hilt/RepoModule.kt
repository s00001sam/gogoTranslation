package com.sam.gogotranslation.hilt

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.sam.gogotranslation.BuildConfig
import com.sam.gogotranslation.repo.datasource.BaseDataSource
import com.sam.gogotranslation.repo.datasource.LocalDataSource
import com.sam.gogotranslation.repo.datasource.RemoteDataSource
import com.sam.gogotranslation.repo.repository.TranslationRepository
import com.sam.gogotranslation.repo.room.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {
    @Singleton
    @Provides
    fun providerGeminiModel(): GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",
        apiKey = BuildConfig.GEMINI_KEY
    )

    @Singleton
    @LocalData
    @Provides
    fun providerLocalDataSource(db: MyDatabase): BaseDataSource {
        return LocalDataSource(db)
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
    ) = TranslationRepository(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context) = MyDatabase.getInstance(context)
}

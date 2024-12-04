package com.sam.gogotranslation.hilt

import android.content.Context
import com.sam.gogotranslation.repo.repository.BaseRepository
import com.sam.gogotranslation.repo.usecase.DeleteTranslationUseCase
import com.sam.gogotranslation.repo.usecase.GetSingleTranslationUseCase
import com.sam.gogotranslation.repo.usecase.GetTranslationsUseCase
import com.sam.gogotranslation.repo.usecase.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideTranslateUseCase(
        repository: BaseRepository,
        @ApplicationContext context: Context,
    ) = TranslateUseCase(context, repository)

    @Provides
    fun provideGetSingleTranslationUseCase(repository: BaseRepository) =
        GetSingleTranslationUseCase(repository)

    @Provides
    fun provideGetTranslationsUseCase(repository: BaseRepository) =
        GetTranslationsUseCase(repository)

    @Provides
    fun provideDeleteTranslationUseCase(repository: BaseRepository) =
        DeleteTranslationUseCase(repository)
}

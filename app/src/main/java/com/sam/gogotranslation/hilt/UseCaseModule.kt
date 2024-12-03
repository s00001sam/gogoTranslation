package com.sam.gogotranslation.hilt

import android.content.Context
import com.sam.gogotranslation.repo.repository.TranslationRepository
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
        repository: TranslationRepository,
        @ApplicationContext context: Context,
    ) = TranslateUseCase(context, repository)

    @Provides
    fun provideGetSingleTranslationUseCase(repository: TranslationRepository) =
        GetSingleTranslationUseCase(repository)

    @Provides
    fun provideGetTranslationsUseCase(repository: TranslationRepository) =
        GetTranslationsUseCase(repository)

    @Provides
    fun provideDeleteTranslationUseCase(repository: TranslationRepository) =
        DeleteTranslationUseCase(repository)
}

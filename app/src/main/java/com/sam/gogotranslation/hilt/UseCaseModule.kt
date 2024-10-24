package com.sam.gogotranslation.hilt

import com.sam.gogotranslation.repo.repository.TranslationRepository
import com.sam.gogotranslation.repo.usecase.TranslationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideTranslationUseCase(repository: TranslationRepository) =
        TranslationUseCase(repository)
}

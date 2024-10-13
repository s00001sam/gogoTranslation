package com.sam.gogotranslation.hilt

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalData

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteData

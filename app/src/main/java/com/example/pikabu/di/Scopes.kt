package com.example.pikabu.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AllFeedScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SavedFeedScope
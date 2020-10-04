package com.example.pikabu.di

import com.example.pikabu.data.local.Dao
import com.example.pikabu.data.repository.Repository
import com.example.pikabu.data.network.API
import com.example.pikabu.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFeedRepository(api: API, dao: Dao): Repository {
        return RepositoryImpl(api, dao)
    }
}
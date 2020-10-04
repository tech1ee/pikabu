package com.example.pikabu.di

import android.content.Context
import androidx.room.Room
import com.example.pikabu.data.local.AppDatabase
import com.example.pikabu.data.local.Dao
import com.example.pikabu.data.network.API
import com.example.pikabu.data.network.ConnectionInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pikabu.ru/page/interview/mobile-app/test-api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectionInterceptor(context))
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideAPI(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java,"PikabuDB").build()
    }

    @Provides
    fun provideDao(db: AppDatabase): Dao {
        return db.dao()
    }
}
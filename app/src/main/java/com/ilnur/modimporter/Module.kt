package com.ilnur.modimporter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ilnur.modimporter.database.AppDatabase
import com.ilnur.modimporter.database.ModDao
import com.ilnur.modimporter.repository.FavouritesRepository
import com.ilnur.modimporter.repository.HomeRepository
import com.ilnur.modimporter.repository.ModinfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {
    @Provides
    fun provideModDao(@ApplicationContext context: Context):
            ModDao = AppDatabase(context).modsDao()
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase = AppDatabase(context)

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideHomeRepository(
        @ApplicationContext context: Context,
        db: AppDatabase) = HomeRepository(context, db)

    @Provides
    fun provideModinfoRepository(
        @ApplicationContext context: Context,
        db: AppDatabase) = ModinfoRepository(context, db)

    @Provides
    fun provideFavouritesRepository(
        @ApplicationContext context: Context,
        db: AppDatabase) = FavouritesRepository(context, db)

}
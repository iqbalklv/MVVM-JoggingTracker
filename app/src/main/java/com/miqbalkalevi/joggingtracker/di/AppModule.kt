package com.miqbalkalevi.joggingtracker.di

import android.app.Application
import androidx.room.Room
import com.miqbalkalevi.joggingtracker.data.database.JogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJogDatabase(app: Application): JogDatabase =
        Room.databaseBuilder(app, JogDatabase::class.java, "jog_database")
            .build()

    @Provides
    @Singleton
    fun provideJogDao(jogDatabase: JogDatabase) = jogDatabase.getJogDao()
}
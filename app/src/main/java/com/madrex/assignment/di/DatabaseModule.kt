package com.madrex.assignment.di

import android.content.Context
import androidx.room.Room
import com.madrex.assignment.roomDb.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun getRoomDatabase(@ApplicationContext context: Context) : UserDB {
        return Room.databaseBuilder(context,UserDB::class.java,"UserDB").build()
    }
}
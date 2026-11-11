package com.example.kidcreds.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.kidcreds.Const.USER_DATABASE
import com.example.kidcreds.data.local.database.KidCredsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "log_in")

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): KidCredsDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = KidCredsDatabase::class.java,
                name = USER_DATABASE
            )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideDao(kidCredsDatabase: KidCredsDatabase) = kidCredsDatabase.userDao()

    @Provides
    fun provideChoreDao(kidCredsDatabase: KidCredsDatabase) = kidCredsDatabase.choreDao()


    @Provides
    @Singleton
    fun provideDatastore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore
}
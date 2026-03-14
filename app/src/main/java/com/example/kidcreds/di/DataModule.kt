package com.example.kidcreds.di

import android.content.Context
import androidx.room.Room
import com.example.kidcreds.Const.USER_DATABASE
import com.example.kidcreds.data.local.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = UserDatabase::class.java,
                name = USER_DATABASE
            )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideDao(userDatabase: UserDatabase) = userDatabase.userDao()

}
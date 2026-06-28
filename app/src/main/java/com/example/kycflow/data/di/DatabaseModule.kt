package com.example.kycflow.data.di

import android.content.Context
import androidx.room.Room
import com.example.kycflow.data.database.CustomerDao
import com.example.kycflow.data.database.CustomerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCustomerDatabase(@ApplicationContext context: Context): CustomerDatabase {
        return Room.databaseBuilder(
            context,
            CustomerDatabase::class.java,
            "customer_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(database: CustomerDatabase): CustomerDao {
        return database.customerDao()
    }
}

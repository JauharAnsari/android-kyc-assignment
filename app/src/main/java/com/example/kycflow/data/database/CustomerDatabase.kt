package com.example.kycflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CustomerEntity::class], version = 1, exportSchema = false)
abstract class CustomerDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}

package com.example.kycflow.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val dateOfBirth: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String,
    val balance: Double,
    val assignedIfsc: String,
    val kycVerified: Boolean = false,
    val localSelfiePath: String? = null,
    
    // Resolved bank details from IFSC
    val bankName: String? = null,
    val branchName: String? = null,
    val bankCity: String? = null,
    val bankState: String? = null,
    val micr: String? = null,
    
    val lastUpdated: Long = System.currentTimeMillis()
)

package com.example.kycflow.domain.model

data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val accountNumber: String,
    val balance: Double,
    val isVerified: Boolean = false,
    val accountType: String = "Savings"
) {
    val fullName: String
        get() = "$firstName $lastName"
        
    val maskedAccountNumber: String
        get() = if (accountNumber.length > 4) {
            "**** **** **** ${accountNumber.takeLast(4)}"
        } else {
            accountNumber
        }
}

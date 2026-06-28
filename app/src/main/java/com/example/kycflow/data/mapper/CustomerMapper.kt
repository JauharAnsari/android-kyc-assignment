package com.example.kycflow.data.mapper

import com.example.kycflow.data.database.CustomerEntity
import com.example.kycflow.data.model.DummyJsonUser
import com.example.kycflow.domain.model.Customer
import kotlin.random.Random

fun DummyJsonUser.toEntity(assignedIfsc: String, balance: Double): CustomerEntity {
    return CustomerEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        image = image,
        dateOfBirth = birthDate,
        email = email,
        phone = phone,
        address = address.address,
        city = address.city,
        state = address.state,
        country = address.country ?: "India",
        cardNumber = bank.cardNumber,
        cardType = bank.cardType,
        currency = bank.currency,
        iban = bank.iban,
        balance = balance,
        assignedIfsc = assignedIfsc,
        kycVerified = false,
        localSelfiePath = null,
        bankName = null,
        branchName = null,
        bankCity = null,
        bankState = null,
        micr = null,
        lastUpdated = System.currentTimeMillis()
    )
}

fun CustomerEntity.toDomainModel(): Customer {
    return Customer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        image = localSelfiePath ?: image, // Use local selfie if verified
        accountNumber = iban,
        balance = balance,
        isVerified = kycVerified,
        accountType = cardType // Use card type as account type for chip filtering
    )
}

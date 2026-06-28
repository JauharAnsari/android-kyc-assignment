package com.example.kycflow.data.model

import com.google.gson.annotations.SerializedName

data class DummyJsonUsersResponse(
    val users: List<DummyJsonUser>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class DummyJsonUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val birthDate: String,
    val phone: String,
    val email: String,
    val address: Address,
    val bank: Bank
)

data class Address(
    val address: String,
    val city: String,
    val state: String,
    val country: String? = "USA" // DummyJSON doesn't return country usually, adding a default
)

data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)

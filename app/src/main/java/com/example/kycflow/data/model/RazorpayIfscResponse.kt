package com.example.kycflow.data.model

import com.google.gson.annotations.SerializedName

data class RazorpayIfscResponse(
    @SerializedName("BANK") val bank: String,
    @SerializedName("IFSC") val ifsc: String,
    @SerializedName("BRANCH") val branch: String,
    @SerializedName("CITY") val city: String,
    @SerializedName("STATE") val state: String,
    @SerializedName("MICR") val micr: String
)

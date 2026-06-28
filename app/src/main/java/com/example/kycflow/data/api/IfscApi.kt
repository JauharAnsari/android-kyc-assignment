package com.example.kycflow.data.api

import com.example.kycflow.data.model.RazorpayIfscResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IfscApi {
    @GET("{ifsc}")
    suspend fun getBankDetails(@Path("ifsc") ifsc: String): RazorpayIfscResponse
}

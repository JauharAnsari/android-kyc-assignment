package com.example.kycflow.data.api

import com.example.kycflow.data.model.DummyJsonUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomerApi {
    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0
    ): DummyJsonUsersResponse
}

package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.LogResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentLogApi {
    @GET("/paymentLog/{userId}")
    fun getLog(@Path("userId") userId:String): Call<List<LogResponse>>
}
package com.example.bidhubandroid

import com.example.bidhubandroid.api.AuctionItemApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val client = Retrofit.Builder()
            .baseUrl("http://172.30.1.18:3977")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val auctionItemApi: AuctionItemApi = client.create(AuctionItemApi::class.java)
    }
}

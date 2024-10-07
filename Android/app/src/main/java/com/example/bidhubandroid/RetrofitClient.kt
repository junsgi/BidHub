package com.example.bidhubandroid

import android.content.Context
import android.util.Log
import com.example.bidhubandroid.api.AuctionItemApi
import com.example.bidhubandroid.api.MemberApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

class RetrofitClient {
    companion object {
        private val client = Retrofit.Builder()
            //192.168.0.19
            //10.30.2.196
            .baseUrl("http://172.30.1.25:3977")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val auctionItemApi: AuctionItemApi = client.create(AuctionItemApi::class.java)
        val memberApi: MemberApi = client.create(MemberApi::class.java)

    }


}
package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.AuctionListReponse
import com.example.bidhubandroid.api.data.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuctionItemApi {

    @GET("/auctionitem")
    suspend fun getAuctionItems(@Query("st") st:Int, @Query("sort") sort:Int, @Query("id") id:String?): AuctionListReponse

    @GET("/auctionitem/{aitemId}")
    fun getItem(@Path("aitemId") id:String): Call<DetailResponse>

}
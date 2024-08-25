package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.AuctionListReponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuctionItemApi {

    @GET("/auctionitem")
    fun getAuctionItems(@Query("st") st:Int, @Query("sort") sort:Int, @Query("id") id:String?): Call<AuctionListReponse>

}
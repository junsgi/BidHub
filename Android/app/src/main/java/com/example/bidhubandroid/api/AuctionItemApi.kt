package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.AuctionListReponse
import com.example.bidhubandroid.api.data.BiddingRequest
import com.example.bidhubandroid.api.data.DetailResponse
import com.example.bidhubandroid.api.data.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface AuctionItemApi {

    @GET("/auctionitem")
    suspend fun getAuctionItems(@Query("st") st:Int, @Query("sort") sort:Int, @Query("id") id:String?): AuctionListReponse

    @GET("/auctionitem/{aitemId}")
    fun getItem(@Path("aitemId") id:String): Call<DetailResponse>

    @Multipart
    @POST("/auctionitem/submit")
    fun submit(
        @Part image: MultipartBody.Part?,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ResponseBody>

    @POST("auction/bidding")
    fun bidding(@Body body:BiddingRequest): Call<ResponseBody>

    @POST("auction/bidding/immediately")
    fun bidding_imm(@Body body:BiddingRequest): Call<ResponseBody>

    @POST("auction/close")
    fun close(@Body body:BiddingRequest): Call<ResponseBody>

    @POST("auction/decide")
    fun decide(@Body body:BiddingRequest): Call<ResponseBody>

    @DELETE("auctionitem/{id}")
    fun remove(@Path("id") id:String): Call<ResponseBody>
}
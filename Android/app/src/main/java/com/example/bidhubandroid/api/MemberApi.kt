package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.api.data.ResponseBody
import com.example.bidhubandroid.api.data.SignupBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberApi {
    @POST("member/login")
    fun login(@Body body: LoginBody): Call<ResponseBody>

    @POST("member/signup")
    fun signup(@Body body:SignupBody): Call<ResponseBody>

    @GET("member/detail/{id}")
    fun detail(@Path("id") id:String): Call<ResponseBody>

    @POST("member/point")
    fun kakaopay(@Query("from") from:String, @Body body: KakaoPointRequest): Call<ResponseBody>
}
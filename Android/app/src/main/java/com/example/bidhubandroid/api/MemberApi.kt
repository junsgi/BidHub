package com.example.bidhubandroid.api

import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.api.data.ResponseBody
import com.example.bidhubandroid.api.data.SignupBody
import com.example.bidhubandroid.api.data.TossRequest
import com.example.bidhubandroid.api.data.UpdateRequest
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

    @POST("member/toss")
    fun toss(@Body body: TossRequest): Call<ResponseBody>

    @POST("member/update/passwd")
    fun updatePW(@Body body: UpdateRequest): Call<ResponseBody>

    @POST("member/update/nickname")
    fun updateNICK(@Body body: UpdateRequest): Call<ResponseBody>
}
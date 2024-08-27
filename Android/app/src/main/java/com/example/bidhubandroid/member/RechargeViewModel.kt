package com.example.bidhubandroid.member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class RechargeViewModel : ViewModel() {

    fun kakaopay(body: KakaoPointRequest, onSuccess:(s:String)->Unit) = viewModelScope.launch(Dispatchers.IO) {
        RetrofitClient.memberApi.kakaopay("android_app_scheme", body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: retrofit2.Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val editor = UserSharedPreferences.sharedPreferences.edit()
                    val res = response.body()!!
                    val list = res.message?.split("_")!!
                    val URL = list[0];
                    Log.d("URLjun", URL)
                    editor.putString("tid", list[1])
                    editor.putString("orderId", "${list[2]}_${list[3]}_${list[4]}")
                    onSuccess(URL)
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
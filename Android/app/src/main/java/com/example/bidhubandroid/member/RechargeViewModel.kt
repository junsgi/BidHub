package com.example.bidhubandroid.member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.ResponseBody
import com.example.bidhubandroid.api.data.TossRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RechargeViewModel : ViewModel() {
    fun toss(body: TossRequest, onSuccess: (res: Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.toss(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()?.status ?: true)
                    } else {
                        onSuccess(response.body()?.status ?: false)
                    }
                } // onResponse

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("getDetail", t.toString())
                }
            })// retrofit
        }
    }
}
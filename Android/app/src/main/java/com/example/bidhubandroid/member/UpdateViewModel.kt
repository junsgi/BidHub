package com.example.bidhubandroid.member

import android.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.ResponseBody
import com.example.bidhubandroid.api.data.UpdateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateViewModel : ViewModel() {
    fun updatePW(body: UpdateRequest, callback: (res:ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.updatePW(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        val body = response.body()!!
                        callback(body)
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }

            })
        }
    }

    fun updateNICK(body: UpdateRequest, callback: (res: ResponseBody) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.updateNICK(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        val res = response.body()!!
                        callback(res)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }

            })
        }
}
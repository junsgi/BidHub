package com.example.bidhubandroid.paymentlog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.LogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentLogViewModel : ViewModel() {

    fun getLog(userId:String, onSuccess: (res: List<LogResponse>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.paymentLogApi.getLog(userId).enqueue(object : Callback<List<LogResponse>> {
                override fun onResponse(
                    call: Call<List<LogResponse>>,
                    response: Response<List<LogResponse>>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse

                override fun onFailure(call: Call<List<LogResponse>>, t: Throwable) {
                    Log.d("getDetail", t.toString())
                }
            })// retrofit
        }
    }
}
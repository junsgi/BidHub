package com.example.bidhubandroid.auction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistViewModel : ViewModel() {
    fun submit(imagePart: MultipartBody.Part?, dataMap: Map<String, RequestBody>, onSuccess: (status:Boolean?, message:String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.submit(imagePart, dataMap).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val res = response.body()!!
                        onSuccess(res.status, res.message)
                    }else {
                        onSuccess(false, "서버오류")
                    }
                } // onResponse

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("getDetail", t.toString())
                }
            })// retrofit
        }
    }
}
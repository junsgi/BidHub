package com.example.bidhubandroid.auction

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.DetailResponse
import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    fun getDetail(aid : String, onSuccess: (res:DetailResponse) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.getItem(aid).enqueue(object : Callback<DetailResponse> {
                override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }else {
                        onSuccess(DetailResponse("error","error","error","error","error",-1,"error",-1,"error","error"))
                    }
                } // onResponse

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("getDetail", t.toString())
                }
            })// retrofit
        }
    }
}
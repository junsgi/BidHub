package com.example.bidhubandroid.auction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.BiddingRequest
import com.example.bidhubandroid.api.data.DetailResponse
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

    fun bidding(body:BiddingRequest, onSuccess: (res: ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.bidding(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("bidding", t.toString())
                }
            })// retrofit
        }
    }
    fun bidding_imm(body:BiddingRequest, onSuccess: (res: ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.bidding_imm(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("bidding_imm", t.toString())
                }
            })// retrofit
        }
    }
    fun close(body:BiddingRequest, onSuccess: (res: ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.close(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("close", t.toString())
                }
            })// retrofit
        }
    }

    fun decide(body:BiddingRequest, onSuccess: (res: ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.decide(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("decide", t.toString())
                }
            })// retrofit
        }
    }

    fun remove(body:BiddingRequest, onSuccess: (res: ResponseBody) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.auctionItemApi.remove(body.itemId!!).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        onSuccess(response.body()!!)
                    }
                } // onResponse
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("remove", t.toString())
                }
            })// retrofit
        }
    }
}
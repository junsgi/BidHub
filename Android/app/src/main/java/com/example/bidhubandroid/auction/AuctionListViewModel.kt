package com.example.bidhubandroid.auction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.AuctionListReponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuctionListViewModel : ViewModel() {

    val items = MutableLiveData<AuctionListReponse>()

    fun getItems(st:Int, sort:Int, id:String?) = viewModelScope.launch(Dispatchers.IO) {
        RetrofitClient.auctionItemApi.getAuctionItems(st, sort, id).enqueue(object : Callback<AuctionListReponse>{
            override fun onResponse(
                call: Call<AuctionListReponse>,
                response: Response<AuctionListReponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    items.value = response.body()
                }
            }

            override fun onFailure(call: Call<AuctionListReponse>, t: Throwable) {
                Log.d("auctionItemViewModelFail", t.toString())
            }
        })
    }
}
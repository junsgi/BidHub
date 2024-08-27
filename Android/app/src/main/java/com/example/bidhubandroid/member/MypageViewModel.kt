package com.example.bidhubandroid.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageViewModel : ViewModel() {

    fun detail(id:String, onSuccess:()->Unit) {
        val edit = UserSharedPreferences.sharedPreferences.edit()
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.detail(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val res = response.body()
                    if (response.isSuccessful && response.code() == 200 && res != null) {
                        edit.run {
                            edit.putString("id", id)
                            edit.putString("nickname", res.nickname)
                            edit.putLong("point", res.point ?: -1)
                            commit()
                        }
                    }
                    onSuccess()
                } // onResponse

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })// retrofit
        }
    }
}
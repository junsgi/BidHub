package com.example.bidhubandroid.member

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.api.data.ResponseBody
import com.example.bidhubandroid.api.data.SignupBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    fun signup(body : SignupBody, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.signup(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val res = response.body()
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("알림")
                        builder.setMessage(res?.message)
                        builder.setNegativeButton("확인") { dialog, which ->
                            dialog.dismiss() // 대화상자 닫기
                            if (res?.status == true){
                                onSuccess()
                            }
                        }
                        builder.show()
                    }else {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("알림")
                        builder.setMessage("server error")
                        builder.setNegativeButton("확인") { dialog, which ->
                            dialog.dismiss() // 대화상자 닫기
                        }
                        builder.show()
                    }
                } // onResponse

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })// retrofit
        }
    }
}
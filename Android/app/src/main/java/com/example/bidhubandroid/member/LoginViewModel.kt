package com.example.bidhubandroid.member

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun login(body : LoginBody, context: Context, onSuccess: () -> Unit) {
        val edit = UserSharedPreferences.sharedPreferences.edit()
        edit.clear().apply()
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.memberApi.login(body).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val res = response.body()
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("알림")
                        builder.setMessage(res?.message)
                        builder.show()
                        edit.run {
                            edit.putString("id", body.id)
                            edit.putString("nickname", res!!.nickname)
                            edit.putLong("point", res!!.point ?: -1)
                            onSuccess()

                            apply()
                        }
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
                    Log.d("loginError", t.toString())
                }
            })// retrofit
        }
    }
}
package com.example.bidhubandroid.member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.api.data.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class RechargeViewModel : ViewModel() {
}
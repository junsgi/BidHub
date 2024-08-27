package com.example.bidhubandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bidhubandroid.databinding.ActivityPayBinding

class PayActivity(private val response:String) : AppCompatActivity() {
    private val binding by lazy { ActivityPayBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        Log.d("payActivityluckybiki", "개씨발")
    }
}
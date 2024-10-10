package com.example.bidhubandroid

import android.os.SystemClock
import android.view.View

class OnSingleClickListener (
    private var interval: Int = 500,
    private var onSingleClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if ((elapsedRealtime - lastClickTime) < interval) {
            return
        }
        lastClickTime = elapsedRealtime
        onSingleClick(v)
    }
}

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val oneClick = OnSingleClickListener(onSingleClick = onSingleClick)
    setOnClickListener(oneClick)
}
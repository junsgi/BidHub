package com.example.bidhubandroid.api.data

data class DetailResponse(
    val aitemId:String,
    val aitemTitle:String,
    val aitemContent:String,
    val aitemStart:String,
    val aitemBid:String,
    val aitemDate:Long,
    val aitemImmediate:String,
    val aitemCurrent:Long,
    val memId:String,
    val status:String
)
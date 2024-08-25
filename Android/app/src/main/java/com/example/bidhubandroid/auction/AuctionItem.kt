package com.example.bidhubandroid.auction

data class AuctionItem(
    val aitem_id: String,
    val current : Int,
    val immediate : String?,
    val remaining : Long,
    val status : String?,
    val title : String
)
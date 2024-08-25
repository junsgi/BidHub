package com.example.bidhubandroid.api.data

import com.example.bidhubandroid.auction.AuctionItem

data class AuctionListReponse(
    val list:MutableList<AuctionItem>,
    val len:Int
)

package com.example.bidhubandroid.auction

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.data.AuctionItem
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

class AuctionPagingSource() : PagingSource<Int, AuctionItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuctionItem> {
        // Start paging with the STARTING_KEY if this is the first load
        val start = params.key ?: 1

        // Load as many items as hinted by params.loadSize
        val res = RetrofitClient.auctionItemApi.getAuctionItems(start, 0, null)
        return LoadResult.Page(
            data = res.list,
            prevKey = if (start - 1 == 0) null else start - 1,
            nextKey = if (res.list.isNotEmpty()) start + 1 else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, AuctionItem>): Int? {
        // 새로 고침 시 페이지 키를 결정
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }


    }


}
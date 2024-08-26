package com.example.bidhubandroid.auction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bidhubandroid.RetrofitClient
import com.example.bidhubandroid.api.AuctionItemApi
import com.example.bidhubandroid.api.data.AuctionItem
import com.example.bidhubandroid.api.data.AuctionListReponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class AuctionListViewModel() : ViewModel() {

    val items: Flow<PagingData<AuctionItem>> = Pager(
        config = PagingConfig(pageSize = 50, enablePlaceholders = false),
        pagingSourceFactory = { AuctionPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)

}
package com.example.bidhubandroid.auction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bidhubandroid.R
import com.example.bidhubandroid.api.data.AuctionItem
import com.example.bidhubandroid.setOnSingleClickListener
import kotlin.math.floor

open class AuctionItemAdapter() :
    PagingDataAdapter<AuctionItem, AuctionItemAdapter.AuctionItemViewHolder>(AuctionItemComparator) {
    //    private var data : MutableList<AuctionItem> = mutableListOf();
    fun interface OnItemClickListener {
        fun onItemClick(v:View, position:Int)
    }
    private var listener: OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    fun getId(position: Int): String? {
        return getItem(position)?.aitem_id
    }
    // ViewHolder class
    class AuctionItemViewHolder(view: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val current: TextView = view.findViewById(R.id.current)
        val immediate: TextView = view.findViewById(R.id.immediate)
        val remaining: TextView = view.findViewById(R.id.remaining)
        val img: ImageView = view.findViewById(R.id.imgView)
        init {
            view.setOnSingleClickListener {
                listener?.onItemClick(view, this.layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.auction_item, parent, false)
        return AuctionItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: AuctionItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item?.title
        holder.current.text = "현재가 : ${item?.current.toString()}원"
        holder.immediate.text = if (item?.immediate != null) "즉시 구매가 : ${item.immediate}원" else ""
        holder.remaining.text = convertSeconds(item?.remaining ?: -1)
        Glide.with(holder.itemView.context)
            .load("http://localhost:3977/auctionitem/img/${item?.aitem_id}")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.img)
    }
    companion object{
        fun convertSeconds(sec: Long?): String {
            if (sec == null || (sec <= 0)) {
                return "종료된 거래"
            }
            val MINUTE = 60L;
            val HOUR = 3600L;
            val DAY = 86400L;

            var days = 0L;
            var hours = 0L;
            var minutes = 0L;
            var remainingSeconds = sec;

            // days
            days = floor((remainingSeconds / DAY).toDouble()).toLong();
            remainingSeconds %= DAY;

            // hours
            hours = floor((remainingSeconds / HOUR).toDouble()).toLong();
            remainingSeconds %= HOUR;

            // minutes
            minutes = floor((remainingSeconds / MINUTE).toDouble()).toLong();
            remainingSeconds %= MINUTE;

            return "${days}일 ${hours}시 ${minutes}분 남음"
        }
    }


    // DiffUtil.ItemCallback implementation
    object AuctionItemComparator : DiffUtil.ItemCallback<AuctionItem>() {
        override fun areItemsTheSame(oldItem: AuctionItem, newItem: AuctionItem): Boolean {
            return oldItem.aitem_id == newItem.aitem_id
        }

        override fun areContentsTheSame(oldItem: AuctionItem, newItem: AuctionItem): Boolean {
            return oldItem == newItem
        }
    }
}
package com.example.bidhubandroid.auction

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bidhubandroid.R

class DetailAdapter: RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    inner class DetailViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.title)
        val img : ImageView = view.findViewById(R.id.imgView)
        val memId : TextView = view.findViewById(R.id.memId)
        val remaining : TextView = view.findViewById(R.id.remaining)
        val current : TextView = view.findViewById(R.id.current)
        val start : TextView = view.findViewById(R.id.start)
        val bid : TextView = view.findViewById(R.id.bid)
        val content : TextView = view.findViewById(R.id.content)
        val button1 : TextView = view.findViewById(R.id.button1)
        val button2 : TextView = view.findViewById(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
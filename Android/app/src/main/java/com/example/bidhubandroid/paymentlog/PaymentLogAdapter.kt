package com.example.bidhubandroid.paymentlog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bidhubandroid.R
import com.example.bidhubandroid.api.data.LogResponse

class PaymentLogAdapter: RecyclerView.Adapter<PaymentLogAdapter.LogViewHolder>() {
    private var data:List<LogResponse> = mutableListOf()
    fun setData(data: List<LogResponse>) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.paylog_item, parent, false)
        return LogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val item = data[position]
        holder.approve.text = if (item.approvedAt.equals("취소됨")) item.approvedAt else item.approvedAt.replace("T", " ")
        holder.order.text = item.order.replace("T", " ").substring(0, item.order.indexOf("."))
        holder.amount.text = item.amount
        holder.pid.text = item.tid
    }

    class LogViewHolder(view: View): RecyclerView.ViewHolder(view){
        val order:TextView = view.findViewById(R.id.order123)
        val approve:TextView = view.findViewById(R.id.approve123)
        val pid:TextView = view.findViewById(R.id.pid123)
        val amount:TextView = view.findViewById(R.id.amount123)
    }
}
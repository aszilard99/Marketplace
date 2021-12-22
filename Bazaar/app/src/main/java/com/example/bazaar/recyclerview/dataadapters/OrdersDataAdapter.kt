package com.example.bazaar.recyclerview.dataadapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.model.Order
import com.example.bazaar.model.Product

class OrdersDataAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrdersDataAdapter.DataViewHolder>() {

    var  createCounter: Int = 0
    var bindCounter: Int = 0

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ownerTV: TextView = itemView.findViewById(R.id.myFaresItemOwnerTV)
        val titleTV: TextView = itemView.findViewById(R.id.myFaresItemTitleTV)
        val priceTV: TextView = itemView.findViewById(R.id.myFaresItemPriceTV)
        val availabilityTV: TextView = itemView.findViewById(R.id.myFaresAvailabilityTV)
        val messagesTV : TextView = itemView.findViewById(R.id.myFaresMessagesTV)
        val amountTV : TextView = itemView.findViewById(R.id.myFaresAmountTV)
        init {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersDataAdapter.DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.order_layout, parent, false)
        ++createCounter
        Log.d("xxx", "onCreateViewHolder: $createCounter")
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrdersDataAdapter.DataViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.ownerTV.text = currentItem.username
        holder.priceTV.text = "${currentItem.price_per_unit}"
        holder.titleTV.text = currentItem.title




        ++bindCounter
        Log.d("xxx", "onBindViewHolder: $bindCounter")
    }

    override fun getItemCount() = orderList.size

}


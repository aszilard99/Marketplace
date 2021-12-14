package com.example.bazaar.recyclerview.dataadapters

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.model.Product

class TimelineDataAdapter(private val productList: List<Product>, private val listener: OnItemClickListener) : RecyclerView.Adapter<TimelineDataAdapter.DataViewHolder>() {

    var  createCounter: Int = 0
    var bindCounter: Int = 0

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ownerTV: TextView = itemView.findViewById(R.id.timelineItemOwnerTV)
        val titleTV: TextView = itemView.findViewById(R.id.timelineItemTitleTV)
        val priceTV: TextView = itemView.findViewById(R.id.timelineItemPriceTV)
        val orderButton: Button = itemView.findViewById(R.id.timelineOrderButton)
        val availabilityTV: TextView = itemView.findViewById(R.id.timelineAvailabilityTV)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition

            Log.d("xxx", "AdapterPosition: $currentPosition")
            listener.onItemClick(currentPosition)
        }
        }

    override fun getItemCount() = productList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        ++createCounter
        Log.d("xxx", "onCreateViewHolder: $createCounter")
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.ownerTV.text = currentItem.username
        holder.priceTV.text = "${currentItem.price_per_unit} ${currentItem.price_type}"
        holder.titleTV.text = currentItem.title


        if (currentItem.username == MyApplication.username) {
            holder.orderButton.setVisibility(View.INVISIBLE)

        }
        if (currentItem.username != MyApplication.username) {
            holder.orderButton.setVisibility(View.VISIBLE)

        }
        if (currentItem.is_active) {
            holder.availabilityTV.text = "available"
        }
        else{
            holder.availabilityTV.text = "unavailable"

        }
        ++bindCounter
        Log.d("xxx", "onBindViewHolder: $bindCounter")
    }


}


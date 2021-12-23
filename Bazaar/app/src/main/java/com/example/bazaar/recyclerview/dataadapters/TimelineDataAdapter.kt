package com.example.bazaar.recyclerview.dataadapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.model.Product

class TimelineDataAdapter(private val username: String ,private val productList: List<Product>, private val listener: OnItemClickListener, private val orderListener: OnOrderButtonClickListener) : RecyclerView.Adapter<TimelineDataAdapter.DataViewHolder>() {

    var  createCounter: Int = 0
    var bindCounter: Int = 0



    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    //the fragment that contains the recyclerView will extend this interface,
    // delegating the task to the fragment from the DataAdapter
    interface OnOrderButtonClickListener{
        fun onOrderButtonClick(position: Int)
    }


    interface myOnClickListener : View.OnClickListener {
        fun OnOrderClick(position: Int)
    }

    inner class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, myOnClickListener {
        val ownerTV: TextView = itemView.findViewById(R.id.timelineItemOwnerTV)
        val titleTV: TextView = itemView.findViewById(R.id.timelineItemTitleTV)
        val priceTV: TextView = itemView.findViewById(R.id.timelineItemPriceTV)
        val orderButton: Button = itemView.findViewById(R.id.timelineOrderButton)
        val availabilityTV: TextView = itemView.findViewById(R.id.timelineAvailabilityTV)

        init{
            itemView.setOnClickListener(this)
            orderButton.setOnClickListener {
                OnOrderClick(this.adapterPosition)
            }
        }

        override fun OnOrderClick(position: Int) {
            Log.d("xxx", "AdapterPosition: $position")
            orderListener.onOrderButtonClick(position)
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




        if (currentItem.username == username) {
            holder.orderButton.setVisibility(View.INVISIBLE)

        }
        if (currentItem.username != username) {
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


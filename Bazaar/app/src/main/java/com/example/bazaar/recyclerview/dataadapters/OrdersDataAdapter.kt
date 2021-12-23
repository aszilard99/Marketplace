package com.example.bazaar.recyclerview.dataadapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.model.Order

class OrdersDataAdapter(private val username: String, private val orderList: List<Order>, private val listener: OrdersDataAdapter.OnItemClickListener, private val orderListener: OrdersDataAdapter.OnAcceptButtonClickListener) : RecyclerView.Adapter<OrdersDataAdapter.DataViewHolder>() {

    var  createCounter: Int = 0
    var bindCounter: Int = 0

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    //the fragment that contains the recyclerView will extend this interface,
    // delegating the task to the fragment from the DataAdapter
    interface OnAcceptButtonClickListener{
        fun onAcceptButtonClick(position: Int)
        fun onDeclineButtonClick(position: Int)
    }


    interface myOnClickListener : View.OnClickListener {
        fun OnAcceptClick(position: Int)
        fun OnDeclineClick(position: Int)
    }


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        , View.OnClickListener, OrdersDataAdapter.myOnClickListener {
        val ownerTV: TextView = itemView.findViewById(R.id.myFaresItemOwnerTV)
        val titleTV: TextView = itemView.findViewById(R.id.myFaresItemTitleTV)
        val availabilityTV: TextView = itemView.findViewById(R.id.myFaresAvailabilityTV)
        val messagesTV : TextView = itemView.findViewById(R.id.myFaresMessagesTV)
        val amountTV : TextView = itemView.findViewById(R.id.myFaresAmountTV)
        val acceptButton : Button = itemView.findViewById(R.id.order_layout_accept_order_button)

        val declineButton : Button = itemView.findViewById(R.id.order_layout_decline_order_button)

        init {
            acceptButton.setOnClickListener {
                OnAcceptClick(this.adapterPosition)
            }
            declineButton.setOnClickListener {
                OnDeclineClick(this.adapterPosition)
            }
        }

        override fun OnAcceptClick(position: Int) {
            Log.d("xxx", "AdapterPosition: $position")
            orderListener.onAcceptButtonClick(position)
        }
        override fun OnDeclineClick(position: Int) {
            Log.d("xxx", "AdapterPosition: $position")
            orderListener.onDeclineButtonClick(position)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition

            Log.d("xxx", "AdapterPosition: $currentPosition")
            listener.onItemClick(currentPosition)
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
        holder.titleTV.text = currentItem.title
        holder.availabilityTV.text = currentItem.status
        holder.amountTV.text = "amount: ${currentItem.units}"
        holder.acceptButton.visibility = View.INVISIBLE
        holder.declineButton.visibility = View.INVISIBLE

        if (currentItem.owner_username == username && currentItem.status == "OPEN"){
            holder.acceptButton.visibility = View.VISIBLE
            holder.declineButton.visibility = View.VISIBLE
        }

        if (!currentItem.messages.isEmpty()) {
            holder.messagesTV.visibility = View.VISIBLE
            holder.messagesTV.text = currentItem.messages.get(0).message
        }else{
            holder.messagesTV.visibility = View.GONE
        }





        ++bindCounter
        Log.d("xxx", "onBindViewHolder: $bindCounter")
    }

    override fun getItemCount() = orderList.size

}


package com.example.bazaar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazaar.MyApplication
import com.example.bazaar.model.Order
import com.example.bazaar.model.Product
import com.example.bazaar.repository.Repository
import kotlinx.coroutines.launch
import java.io.IOException

class OrdersViewModel(private val repository: Repository) : ViewModel() {
    var orders : MutableLiveData<List<Order>> = MutableLiveData()
    var currentOrder = Order("", "", "", "", "","", "", "", listOf(), 0, listOf())

    init{
        getOrders()
    }

    private fun getOrders() {
        viewModelScope.launch {
            //the request for all the orders is made because with the addition of useless characters: " \, the backend's filter is unusable
            //its much slower
            try {
                val result = repository.getOrders(MyApplication.token, 512)
                orders.value = result.orders
                Log.d("xxx", "OrderListViewModel - #products:  ${result.item_count}")
            }catch(e: IOException){
                Log.d("xxx", "OrderListViewModel exception: ${e.toString()}")
            }
        }
    }

}
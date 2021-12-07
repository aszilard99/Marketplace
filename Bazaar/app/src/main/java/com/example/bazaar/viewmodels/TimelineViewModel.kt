package com.example.bazaar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazaar.MyApplication
import com.example.bazaar.model.Product
import com.example.bazaar.model.ProductFilter
import com.example.bazaar.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class TimelineViewModel(private val repository: Repository) : ViewModel() {
    var products : MutableLiveData<List<Product>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${MyApplication.token}")
        getProducts()
    }

    private fun getProducts() {
        //laucnhes the coroutine with its lifecycle tied to the owner: TimelineViewModel
        //if TimelineViewModel gets destroyed, this coroutine stops
        viewModelScope.launch{
            try {
                val result = repository.getProducts(MyApplication.token)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")

            }catch (e: Exception) {
                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")

            }
        }
    }

    fun clearFilters(){
        getProducts()
    }
    //TODO throws error when the title contains special characters like: "á"
    fun getFilteredProductsByTitle(title: String){
        var filter = "{\"title\" : \"$title\"}"
        Log.d("xxx", filter.toString())
        viewModelScope.launch{
            try {


                val result = repository.getProductsFilteredByTitle(MyApplication.token, filter)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")

            }catch (e: Exception) {

                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")

            }
        }
    }
}
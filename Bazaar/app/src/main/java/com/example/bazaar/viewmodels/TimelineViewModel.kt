package com.example.bazaar.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bazaar.model.Product
import com.example.bazaar.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class TimelineViewModel(private val repository: Repository, private val sharedPreferences: SharedPreferences) : ViewModel() {
    var products : MutableLiveData<List<Product>> = MutableLiveData()
    var currentProduct = Product(0.0, "", "", "", "",false, "", "", "", "", listOf(), 0, listOf())
    var token : String = ""
    init {
        // Log.d("xxx", "ListViewModel constructor - Token: ${MyApplication.token}")
        getProducts()
    }


    fun refreshProducts(){
        getProducts()
    }

    private fun getProducts() {
        //laucnhes the coroutine with its lifecycle tied to the owner: TimelineViewModel
        //if TimelineViewModel gets destroyed, this coroutine stops
        viewModelScope.launch{
            try {
                val token = sharedPreferences?.getString("token", "").toString()
                val result = repository.getProducts(token , 512)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")

            }catch (e: Exception) {
                Log.d("xxx", "ProductListViewMofdel exception: ${e.toString()}")

            }
        }
    }


    //TODO throws error when the title contains special characters like: "á"
    /*fun getFilteredProducts(filter: String){
        Log.d("xxx", filter.encodeUtf8().toString())
        viewModelScope.launch{
            try {


                val result = repository.getProductsFilteredByTitle(MyApplication.token, filter)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")

            }catch (e: Exception) {

                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")

            }
        }
    }*/

    fun getFilteredProductsByTitle(title: String){
        var filter = "{\"title\" : \"$title\"}"

    }
}
package com.example.bazaar.repository

import com.example.bazaar.api.RetrofitInstance
import com.example.bazaar.model.*

class Repository {
    suspend fun login(request: LoginRequest) : LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String) : ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
    suspend fun getProductsFilteredByTitle(token:String, filter:ProductFilter) : ProductResponse {
        return RetrofitInstance.api.getProductsFilteredByTitle(token, filter)
    }
}
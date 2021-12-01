package com.example.bazaar.repository

import com.example.bazaar.api.RetrofitInstance
import com.example.bazaar.model.LoginRequest
import com.example.bazaar.model.LoginResponse
import com.example.bazaar.model.ProductResponse

class Repository {
    suspend fun login(request: LoginRequest) : LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String) : ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}
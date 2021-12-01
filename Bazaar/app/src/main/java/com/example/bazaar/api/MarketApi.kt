package com.example.bazaar.api

import com.example.bazaar.model.LoginRequest
import com.example.bazaar.model.LoginResponse
import com.example.bazaar.model.ProductResponse
import com.example.bazaar.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    //TODO if im correct this part have to be extended with a proper ProductRequest data class with a <filter> attribute inside it and more
    @GET(Constants.GET_PRODUCTS_URL)
    suspend fun getProducts(@Header ("token") token: String) : ProductResponse

}
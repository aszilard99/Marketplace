package com.example.bazaar.api

import com.example.bazaar.model.*
import com.example.bazaar.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST(Constants.UPDATE_ORDER_URL)
    suspend fun updateOrder(@Query("order_id") order_id : String ,@Header("token")token : String, @Body request: OrderUpdateRequest) : OrderUpdateResponse


    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse


    @GET(Constants.GET_PRODUCTS_URL)
    suspend fun getProducts(@Header ("token") token: String, @Header ("limit") limit : Int) : ProductResponse


    //TODO check if this can be removed
    @GET(Constants.GET_PRODUCTS_URL)
    suspend fun getProductsFilteredByTitle(@Header("token")token: String, @Header ("filter")filter : String) : ProductResponse

    @Multipart
    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(
        @Header ("token") token : String,
        @Part ("title") title : String,
        @Part ("description") description : String,
        @Part ("price_per_unit") price_per_unit : String,
        @Part ("units") units : String,
        @Part ("is_active") is_active : Boolean,
        @Part ("rating") rating : Double,
        @Part ("amount_type") amount_type : String,
        @Part ("price_type") price_type : String,
    ): AddProductResponse

    @POST(Constants.REMOVE_PRODUCT_URL)
    suspend fun removeProduct(@Header("token") token : String, @Query ("product_id") product_id: String) : DeleteProductResponse


    @Multipart
    @POST(Constants.AdD_ORDER_URL)
    suspend fun addOrder(
        @Header ("token") token : String,
        @Part ("title") title : String,
        @Part ("description") description : String,
        @Part ("price_per_unit") price_per_unit : String,
        @Part ("units") units : String,
        @Part ("owner_username") owner_username : String,
        //@Part ("revolut_link") revolut_link : String
    )

    @Multipart
    @POST(Constants.UPDATE_USER_URL)
    suspend fun updateUser(
        @Header ("token") token : String,
        @Part ("phone_number") phone_number : String,
        @Part("email") email : String,
        @Part ("username") username : String
    ): UpdateUserResponse


    @GET(Constants.GET_ORDERS_URL)
    suspend fun getOrders(@Header ("token") token: String, @Header ("limit") limit : Int) : OrderResponse


}
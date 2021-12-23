package com.example.bazaar.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Image(val _id: String, val image_id: String, val image_name: String, val image_path: String)

@JsonClass(generateAdapter = true)
data class Product(
    var rating : Double,
    var amount_type: String,
    var price_type: String,
    var product_id: String,
    var username: String,
    var is_active: Boolean,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    var images: List<Image>,
    var creation_time: Long,
    var messages : List<String>
)


@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)


@JsonClass(generateAdapter = true)
data class DeleteProductResponse(val message: String, val product_id: String, val deletion_time: Long)

@JsonClass(generateAdapter = true)
data class AddProductResponse(
    val creation : String,
    val product_id: String,
    val username: String,
    val is_active: Boolean,
    val price_per_unit: String,
    val units: String,
    val description: String,
    val title: String,
    val rating : Double,
    val amount_type: String,
    val price_type: String,
    val images: List<Image>,
    val creation_time: Long,
)

@JsonClass(generateAdapter = true)
data class ProductFilter(var title: String)
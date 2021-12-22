package com.example.bazaar.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Message(
    var username : String,
    var message_id : String,
    var message : String,
    var creation_time: Long
)

@JsonClass(generateAdapter = true)
data class Order(
    var order_id: String,
    var username: String,
    var status: String,
    var owner_username : String,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    var images: List<Image>,
    //var revolut_link: String,
    var creation_time: Long,
    var messages : List<Message>
)

@JsonClass(generateAdapter = true)
data class OrderResponse(val item_count: Int, val orders: List<Order>, val timestamp: Long)


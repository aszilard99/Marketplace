package com.example.bazaar.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class User(var username: String = "", var password: String = "", val email : String= "")

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
    )

@JsonClass(generateAdapter = true)
data class ResetPasswordRequest (
    var email: String,
    var username: String
)

@JsonClass(generateAdapter = true)
data class ResetPasswordResponse (
    var code: Int,
    var message: String,
    var timestamp : Long
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

@JsonClass(generateAdapter = true)
data class UpdateUserItem (
    var username: String,
    var phone_number: Long,
    var email: String,
    var firebase_token : String,
    var is_activated : Boolean,
    var creation_time: String,
    var token: String,
)

@JsonClass(generateAdapter = true)
data class UpdateUserResponse (
    var code : Int,
    var updatedData : UpdateUserItem,
    var timestamp : Long
)


@JsonClass(generateAdapter = true)
data class RegisterRequest (
    var username: String,
    var password: String,
    var email : String,
    var phone_number : String
)

@JsonClass(generateAdapter = true)
data class RegisterResponse (
    var code : Int,
    var message : String,
    var creation_time : Long

)
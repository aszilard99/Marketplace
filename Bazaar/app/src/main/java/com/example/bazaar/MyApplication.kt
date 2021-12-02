package com.example.bazaar

import android.app.Application

class MyApplication:Application() {
    companion object{
        var token: String = ""
        var username: String = ""
    }
}
package com.example.bazaar.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazaar.MyApplication
import com.example.bazaar.model.LoginRequest
import com.example.bazaar.model.User
import com.example.bazaar.repository.Repository

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token = MutableLiveData<String>()
    var user = MutableLiveData<User>()

    init{
        user.value = User()
    }

    suspend fun login() {
        val request = LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            MyApplication.username = result.username
            token.value = result.token
            Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}
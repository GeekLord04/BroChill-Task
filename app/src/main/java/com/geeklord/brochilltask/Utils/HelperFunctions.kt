package com.geeklord.brochilltask.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Patterns

class HelperFunctions {

    fun registerUserInputValidation(firstName : String? = null, lastName : String? = null, email : String, password : String, isLogin : Boolean) : Pair<Boolean, String>{
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(firstName)) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please fillup all the details")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false,"Please provide valid email address")
        }
        else if (password.length <=5){
            result = Pair(false, "Password must be greater than 5")
        }
        return result
    }

    fun loginUserInputValidation(email : String, password : String, isLogin : Boolean) : Pair<Boolean, String>{
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(email)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please fillup all the details")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false,"Please provide valid email address")
        }
        else if (password.length <=5){
            result = Pair(false, "Password must be greater than 5")
        }
        return result
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
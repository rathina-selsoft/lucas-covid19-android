package com.lucas_charity.covid19.utils

import android.annotation.SuppressLint
import android.content.Context
import com.lucas_charity.covid19.models.User

class SessionManager(private val context: Context) {

    fun clear() {
        val editor = context.getSharedPreferences("COVID19 Data", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    fun setAccessToken(accessToken: String) {
        @SuppressLint("CommitPrefEdits")
        val editor = context.getSharedPreferences("COVID19 Data", Context.MODE_PRIVATE).edit()
        editor.putString("token", accessToken)
        editor.apply()
    }

    fun getAccessToken(): String? {
        val prefs = context.getSharedPreferences("COVID19 Data", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun setUserDetails(user: User) {
        @SuppressLint("CommitPrefEdits")
        val editor = context.getSharedPreferences("COVID19 Data", Context.MODE_PRIVATE).edit()
        editor.putString("fullName", user.fullName)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putInt("userType", user.userType)
        editor.putInt("userId", user.userId)
        editor.putString("phone", user.phone)
        editor.apply()
    }

    fun getUserDetails(): User? {
        val prefs = context.getSharedPreferences("COVID19 Data", Context.MODE_PRIVATE)
        val user = User()
        user.fullName = prefs.getString("fullName", null)
        user.email = prefs.getString("email", null)
        user.password = prefs.getString("password", null)
        user.userType = prefs.getInt("userType", 0)
        user.phone = prefs.getString("phone", null)
        user.userId = prefs.getInt("userId", 0)
        return user
    }

}
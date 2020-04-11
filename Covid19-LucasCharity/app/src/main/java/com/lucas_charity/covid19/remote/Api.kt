package com.lucas_charity.covid19.remote

import com.lucas_charity.covid19.models.User
import com.lucas_charity.covid19.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST




interface Api {
    @POST("covid19/user/register")
    fun registerNewUser(@Body user: User): Call<UserResponse>

    @POST("covid19/user/login")
    fun loginUser(@Body user: User): Call<UserResponse>
}

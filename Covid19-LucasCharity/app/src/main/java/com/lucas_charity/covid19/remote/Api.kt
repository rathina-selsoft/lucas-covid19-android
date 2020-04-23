package com.lucas_charity.covid19.remote

import com.lucas_charity.covid19.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("covid19/covid/addUser")
    fun registerNewUser(@Body user: User): Call<UserResponse>

    @POST("covid19/covid/userLogin")
    fun loginUser(@Body user: User): Call<UserResponse>

    @POST("covid19/covid/addFoodRequest")
    fun addFood(@Body foodRequest: FoodRequest): Call<FoodRequestResponse>

    @POST("covid19/covid/foodRequestHistory")
    fun foodRequestHistory(@Body dateRequest: DateRequest): Call<FoodRequestResponse>
}

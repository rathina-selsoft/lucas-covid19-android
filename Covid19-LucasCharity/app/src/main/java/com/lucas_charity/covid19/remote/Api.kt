package com.lucas_charity.covid19.remote

import com.lucas_charity.covid19.models.FoodDetail
import com.lucas_charity.covid19.models.FoodDetailResponse
import com.lucas_charity.covid19.models.User
import com.lucas_charity.covid19.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST




interface Api {
    @POST("covid19/covid/addUser")
    fun registerNewUser(@Body user: User): Call<UserResponse>

    @POST("covid19/covid/userLogin")
    fun loginUser(@Body user: User): Call<UserResponse>

    @POST("covid19/covid/addFood")
    fun addFood(@Body foodDetail: FoodDetail): Call<FoodDetailResponse>
}

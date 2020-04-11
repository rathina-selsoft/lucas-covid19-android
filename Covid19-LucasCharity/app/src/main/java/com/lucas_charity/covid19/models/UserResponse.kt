package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class UserResponse {

    @SerializedName("success")
    @Expose
    val success: Boolean? = null

    @SerializedName("user")
    @Expose
    val user: User? = null


}
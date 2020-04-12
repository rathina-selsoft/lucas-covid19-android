package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class UserResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("message")
    @Expose
    var message: String? = null


}
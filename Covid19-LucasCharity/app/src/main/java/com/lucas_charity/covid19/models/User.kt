package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("userId")
    @Expose
    var userId: Int = 0

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("userType")
    @Expose
    var userType: Int = 0
}
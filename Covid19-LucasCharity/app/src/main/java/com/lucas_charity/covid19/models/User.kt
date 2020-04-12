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

    @SerializedName("emailId")
    @Expose
    var emailId: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("userType")
    @Expose
    var userType: Int = 0
}
package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FoodRequest {

    @SerializedName("foodId")
    @Expose
    var foodId: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("dateLong")
    @Expose
    var dateLong: Long? = null

    @SerializedName("time")
    @Expose
    var time: String? = null

    @SerializedName("timeLong")
    @Expose
    var timeLong: Long? = null

    @SerializedName("enteredBy")
    @Expose
    var enteredBy: Int? = null

}
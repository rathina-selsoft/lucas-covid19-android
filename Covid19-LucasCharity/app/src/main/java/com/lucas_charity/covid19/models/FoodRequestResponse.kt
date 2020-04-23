package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodRequestResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("foodDetail")
    @Expose
    var foodRequest: FoodRequest? = null

    @SerializedName("foodRequests")
    @Expose
    var foodRequests: MutableList<FoodRequest>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

}
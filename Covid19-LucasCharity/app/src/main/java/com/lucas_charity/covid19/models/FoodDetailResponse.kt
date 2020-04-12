package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodDetailResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("foodDetail")
    @Expose
    var foodDetail: FoodDetail? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

}
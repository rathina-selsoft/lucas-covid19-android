package com.lucas_charity.covid19.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DateRequest {

    @SerializedName("userId")
    @Expose
    var userId: Int? = null

    @SerializedName("fromDate")
    @Expose
    var fromDate: String? = null

    @SerializedName("toDate")
    @Expose
    var toDate: String? = null
}
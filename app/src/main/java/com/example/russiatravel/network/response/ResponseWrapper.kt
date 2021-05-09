package com.example.russiatravel.network.response

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T>(

    @SerializedName ("status")
    var status: String,

    @SerializedName ("status_id")
    var statusId: Int,

    @SerializedName("detail")
    var detail: T
)

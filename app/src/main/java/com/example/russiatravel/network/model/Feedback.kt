package com.example.russiatravel.network.model

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("total_rating")
    val totalRating: Int,
    val feedbacks: List<FeedbackItem>
)

data class FeedbackItem(
    val name: String,
    val rating: Int,
    val feedback: String,
    val photo: String
)

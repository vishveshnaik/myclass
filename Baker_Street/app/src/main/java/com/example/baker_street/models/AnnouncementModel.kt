package com.example.baker_street.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class AnnouncementModel(
    @SerializedName("courseid")
    val courseid: String? = null,

    @SerializedName("text")
    val description: String? = null,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("_id")
    val _id: String? = null,
) : Serializable

data class AnnouncementsModel(
    @SerializedName("announcements")
    val announcementsModel: ArrayList<AnnouncementModel>? = null
)
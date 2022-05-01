package com.ravy.data.vo

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class User(
    val login: String,
    val id: Long,
    val name: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    val followers: Long,
    val following: Long
)
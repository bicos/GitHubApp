package com.ravy.data.dto

import com.google.gson.annotations.SerializedName
import com.ravy.data.vo.Repository

data class SearchResult(
    @SerializedName("total_count") val totalCount: Long,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<Repository>
)
package com.ravy.data.vo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

@Keep
data class Repository(
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val private: Boolean,
    val owner: User,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String,
    val fork: Boolean,
    val url: String,
    @SerializedName("created_at") val createdAt: LocalDateTime,
    @SerializedName("updated_at") val updatedAt: LocalDateTime,
    @SerializedName("pushed_at") val pushedAt: LocalDateTime,
    val homepage: String?,
    val size: Long,
    @SerializedName("stargazers_count") val stargazersCount: Long,
    @SerializedName("watchers_count") val watchersCount: Long,
    val language: String,
    @SerializedName("has_issues") val hasIssues: Boolean,
    @SerializedName("has_projects") val hasProjects: Boolean,
    @SerializedName("has_downloads") val hasDownloads: Boolean,
    @SerializedName("has_wiki") val hasWiki: Boolean,
    @SerializedName("has_pages") val hasPages: Boolean,
    @SerializedName("forks_count") val forksCount: Long,
    @SerializedName("mirror_url") val mirrorUrl: String?,
    val archived: Boolean,
    val disabled: Boolean,
    @SerializedName("open_issues_count") val openIssuesCount: Long,
    val license: License?,
    val allow_forking: Boolean,
    @SerializedName("is_template") val isTemplate: Boolean,
    val topics: List<String>,
    val visibility: Boolean,
    val forks: Long,
    @SerializedName("open_issues") val openIssues: Long,
    val watchers: Long,
    @SerializedName("default_branch") val defaultBranch: String,
    val score: Long
) {
    data class License(
        val key: String,
        val name: String,
        @SerializedName("spdx_id") val spdxId: String,
        val url: String?,
        @SerializedName("node_id") val nodeId: String
    )
}
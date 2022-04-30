package com.ravy.data.api

import com.ravy.data.dto.SearchResult
import com.ravy.data.vo.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiService {

    @GET("/search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String?,
        @Query("page") page: Int,
    ): SearchResult

    @GET("/users/{username}")
    suspend fun getUser(
        @Path("username") userName: String
    ): User

}
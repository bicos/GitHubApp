package com.ravy.data.repo

import com.ravy.data.api.GitApiService
import com.ravy.data.dto.SearchResult
import javax.inject.Inject

interface RepoRepository {
    suspend fun searchRepos(query: String, page: Int): SearchResult
    suspend fun searchReposByUser(userId: String, page: Int): SearchResult
}

class RepoRepositoryImpl @Inject constructor(
    val api: GitApiService
) : RepoRepository {

    override suspend fun searchRepos(query: String, page: Int) =
        api.searchRepos(query, page)

    override suspend fun searchReposByUser(userId: String, page: Int) =
        api.searchRepos("user:${userId}", page)
}
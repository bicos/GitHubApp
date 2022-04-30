package com.ravy.data.repo

import com.ravy.data.api.GitApiService
import com.ravy.data.vo.User
import javax.inject.Inject

interface UserRepository {
    suspend fun getUser(userName: String): User
}

class UserRepositoryImpl @Inject constructor(
    val api: GitApiService
) : UserRepository {

    override suspend fun getUser(userName: String): User {
        return api.getUser(userName)
    }

}
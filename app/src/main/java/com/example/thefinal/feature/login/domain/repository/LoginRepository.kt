package com.bright.easylogin.feature.login.domain.repository

import com.bright.easylogin.feature.login.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun saveUser(user: User)
    fun getUser(): Flow<User?>
}
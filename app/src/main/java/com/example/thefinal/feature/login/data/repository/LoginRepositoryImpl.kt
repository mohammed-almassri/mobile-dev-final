package com.bright.easylogin.feature.login.data.repository

import com.bright.easylogin.feature.login.data.local.PreferencesDataSource
import com.bright.easylogin.feature.login.domain.model.User
import com.bright.easylogin.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
): LoginRepository {
    override suspend fun saveUser(user: User) = preferencesDataSource.saveUser(user)

    override fun getUser(): Flow<User?> = preferencesDataSource.getUser()

}
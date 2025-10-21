package com.bright.easylogin.feature.login.data.local

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.bright.easylogin.feature.login.data.local.DataStoreKeys
import com.bright.easylogin.dataStore
import com.bright.easylogin.feature.login.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesDataSource(private val context: Context) {
    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences: MutablePreferences ->
            preferences[DataStoreKeys.USER_NAME] = user.username
            preferences[DataStoreKeys.PASSWORD] = user.password
        }
    }
    fun getUser(): Flow<User?> {
        return context
            .dataStore
            .data
            .map { preferences: Preferences ->
                val username = preferences[DataStoreKeys.USER_NAME]
                val password = preferences[DataStoreKeys.PASSWORD]
                if (username != null && password != null) {
                    User(username, password)
                } else {
                    null
                }
            }.distinctUntilChanged()
    }
}
package com.bright.easylogin.feature.login.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val USER_NAME = stringPreferencesKey("USER_NAME")
    val PASSWORD = stringPreferencesKey("PASSWORD")
}
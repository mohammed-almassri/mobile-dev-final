package com.bright.easylogin.feature.login.ui.state

data class LoginUiState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val loginSuccess: Boolean = false
)
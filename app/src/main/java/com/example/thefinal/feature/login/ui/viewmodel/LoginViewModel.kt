package com.bright.easylogin.feature.login.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bright.easylogin.feature.login.domain.model.User
import com.bright.easylogin.feature.login.domain.repository.LoginRepository
import com.bright.easylogin.feature.login.ui.state.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val navigateToItems: () -> Unit
): ViewModel() {

    private val _loginUIState = MutableStateFlow(LoginUiState())
    val loginUIState: StateFlow<LoginUiState> = _loginUIState.asStateFlow()


    init {
        viewModelScope.launch {
            val user: Flow<User?> = loginRepository.getUser()
            val existingUser = user.firstOrNull()
            if (existingUser?.username == "admin" && existingUser.password == "admin") {
                Log.d("login", "auto login success")
                navigateToItems()
            }
        }
    }
    fun updateUserName(username: String) {
        _loginUIState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updatePassword(password: String) {
        _loginUIState.update {
            it.copy(
                password = password
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            val username = loginUIState.value.username
            val password = loginUIState.value.password
            if (username == "admin" && password == "admin") {
                Log.d("login", "success")
                loginRepository.saveUser(
                    User(
                        username = username,
                        password = password
                    )
                )
                //navigate to next screen
                navigateToItems()
            }
        }
    }
}
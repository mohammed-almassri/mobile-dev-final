package com.bright.easylogin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bright.easylogin.feature.login.data.local.PreferencesDataSource
import com.bright.easylogin.feature.login.data.repository.LoginRepositoryImpl
import com.bright.easylogin.feature.login.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navigateToCategories: () -> Unit,) {
    Scaffold {innerPadding->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val loginViewModel: LoginViewModel = viewModel {
                LoginViewModel(LoginRepositoryImpl(PreferencesDataSource(context)),navigateToCategories)
            }
            val loginUiState by loginViewModel.loginUIState.collectAsStateWithLifecycle()
            OutlinedTextField(
                value = loginUiState.username,
                onValueChange = { loginViewModel.updateUserName(it) },
                label = { Text("Username") },
            )
            OutlinedTextField(
                value = loginUiState.password,
                onValueChange = { loginViewModel.updatePassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            TextButton(
                onClick = { loginViewModel.login() },
            ) {
                Text("Login")
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navigateToCategories = {})
}
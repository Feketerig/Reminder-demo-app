package com.example.mobilecomputinghomework.feature_authentication.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mobilecomputinghomework.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    mode: String,
    navController: NavHostController
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.LoginScreenEvent.LoginSuccess -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screen.RemindersList.route)
                }
                is LoginViewModel.LoginScreenEvent.LoginFailed -> {
                    Toast.makeText(
                        context,
                        "Wrong email or password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is LoginViewModel.LoginScreenEvent.LogoutFailed -> {
                    Toast.makeText(
                        context,
                        "Logout cancelled",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screen.RemindersList.route)
                }
                is LoginViewModel.LoginScreenEvent.LogoutSuccess -> {
                    Toast.makeText(
                        context,
                        "Logout successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    }

    if (state.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else if (mode == "login") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Please log in",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Email:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.email,
                isError = state.isError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EmailChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Password:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.password,
                isError = state.isError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                }
            )
            if (state.isError){
                Text(
                    text = "Wrong email or password",
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextButton(onClick = { 
                    navController.navigate(Screen.Registration.route) 
                }) {
                    Text(
                        text = "Don't have account? Register here",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    onClick = {
                        viewModel.onEvent(LoginEvent.Login)
                    },
                ) {
                    Text(text = "Log in")
                }
            }
        }
    }else if(mode == "logout"){
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(LoginEvent.CancelLogout)
            },
            title = {
                Text(text = "Close application!")
            },
            text = {
                Text("Are you sure you want to logout?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(LoginEvent.Logout)
                    }) {
                    Text("Exit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(LoginEvent.CancelLogout)
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
}
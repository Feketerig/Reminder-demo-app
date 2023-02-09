package com.example.mobilecomputinghomework.feature_authentication.presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidateEmail
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidatePassword
import com.example.mobilecomputinghomework.feature_authentication.domain.ValidateRepeatedPassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword
): ViewModel() {
    var state by mutableStateOf(RegistrationState())

    private val validationEventChannel = Channel<RegistrationValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when(event) {
            is RegistrationEvent.EmailChanged -> {
                val emailResult = validateEmail.execute(event.email)
                state = state.copy(email = event.email, emailError = emailResult.errorMessage)
            }
            is RegistrationEvent.UserNameChanged -> {
                state = state.copy(userName = event.userName)
            }
            is RegistrationEvent.PasswordChanged -> {
                val passwordResult = validatePassword.execute(event.password)
                state = state.copy(password = event.password, passwordError = passwordResult.errorMessage)
            }
            is RegistrationEvent.RepeatedPasswordChanged -> {
                val repeatedPasswordResult = validateRepeatedPassword.execute(
                    state.password, event.repeatedPassword
                )
                state = state.copy(repeatedPassword = event.repeatedPassword, repeatedPasswordError = repeatedPasswordResult.errorMessage)
            }
            is RegistrationEvent.Login -> {
                register()
            }
        }
    }

    private fun register() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            /*try {
                network.registration(
                    email = state.email,
                    name = state.userName,
                    phone = state.phone,
                    address = state.address,
                    password = state.password
                )
                val token = network.login(state.email, state.password)
                val jwt = JWT(token)
                val id = jwt.getClaim("id").asInt()
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val privilege = when(jwt.getClaim("priv").asString()){
                    "Admin" -> User.Privilege.Admin
                    "User" -> User.Privilege.User
                    "Handler" -> User.Privilege.Handler
                    else -> User.Privilege.User
                }
                MainViewModel.state = MainViewModel.state.copy(id = id, name = name, email = email, privilege = privilege, token = token, password = state.password)
                validationEventChannel.send(RegistrationValidationEvent.Success)
            }catch (e: Exception){
                validationEventChannel.send(RegistrationValidationEvent.Failed)
            }*/
            validationEventChannel.send(RegistrationValidationEvent.Success)
        }
    }

    sealed class RegistrationValidationEvent {
        object Success: RegistrationValidationEvent()
        object Failed: RegistrationValidationEvent()
    }
}
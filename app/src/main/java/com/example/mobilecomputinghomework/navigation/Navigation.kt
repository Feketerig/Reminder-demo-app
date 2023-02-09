package com.example.mobilecomputinghomework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobilecomputinghomework.feature_authentication.presentation.login.LoginScreen
import com.example.mobilecomputinghomework.feature_authentication.presentation.registration.presentation.RegistrationScreen
import com.example.mobilecomputinghomework.feature_profile.presentation.ProfileScreen
import com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_edit.ReminderEditScreen
import com.example.mobilecomputinghomework.feature_reminder.presentation.reminder_list.RemindersListScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ){
        composable(route = Screen.RemindersList.route){
            RemindersListScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.ReminderEdit.route + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            ReminderEditScreen(navHostController = navHostController)
        }
        composable(route = Screen.Registration.route){
            RegistrationScreen(navController = navHostController)
        }
        composable(
            route = Screen.Logout.route,
        ) {
            LoginScreen(
                mode = "logout",
                navController = navHostController
            )
        }
        composable(
            route = Screen.Login.route,
        ) {
            LoginScreen(
                mode = "login",
                navController = navHostController
            )
        }
        composable(route = Screen.Profile.route){
            ProfileScreen("Levente Fazekas")
        }
    }

}
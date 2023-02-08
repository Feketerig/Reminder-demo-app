package com.example.mobilecomputinghomework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputinghomework.login.presentation.LoginScreen
import com.example.mobilecomputinghomework.profile.presentation.ProfileScreen
import com.example.mobilecomputinghomework.registration.presentation.RegistrationScreen
import com.example.mobilecomputinghomework.reminderlist.presentation.RemindersListScreen

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
            RemindersListScreen()
        }
        //Detail Screen
        /*composable(
            route = Screen.SetDetail.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getInt("id")?.let { it1 ->
                //WordSetDetailScreen(it1, navHostController)
            }
        }*/
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
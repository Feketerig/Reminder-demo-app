package com.example.mobilecomputinghomework.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login/login")
    object Logout: Screen("login/logout")
    object Registration: Screen("registration")
    object RemindersList: Screen("list")
    object Profile: Screen("profile")
    object ReminderEdit: Screen("edit")
    object Maps: Screen("maps")
}
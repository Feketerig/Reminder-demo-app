package com.example.mobilecomputinghomework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobilecomputinghomework.navigation.Navigation
import com.example.mobilecomputinghomework.navigation.Screen
import com.example.mobilecomputinghomework.ui.theme.MobileComputingHomeworkTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var showMenu by remember {
                mutableStateOf(false)
            }

            MobileComputingHomeworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {Scaffold(
                    floatingActionButton = {
                        if (navHostController.currentBackStackEntryAsState().value?.destination?.route == Screen.RemindersList.route) {
                            FloatingActionButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add new reminder",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    },
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Reminder")
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            actions = {
                                if (loggedIn) {
                                    IconButton(onClick = { showMenu = !showMenu }) {
                                        Icon(
                                            imageVector = Icons.Filled.MoreVert,
                                            contentDescription = "Menu"
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showMenu,
                                        onDismissRequest = { showMenu = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text(text = "Profile") },
                                            onClick = {
                                                showMenu = false
                                                navHostController.navigate(Screen.Profile.route)
                                            })
                                        DropdownMenuItem(
                                            text = { Text(text = "Logout") },
                                            onClick = {
                                                showMenu = false
                                                navHostController.navigate(Screen.Logout.route)
                                            })
                                    }
                                }
                            }
                        )
                    },

                    ) { paddingValues ->
                    Navigation(
                        navHostController = navHostController,
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
                }
            }
        }
    }
    companion object{
        var loggedIn by mutableStateOf(false)
    }
}
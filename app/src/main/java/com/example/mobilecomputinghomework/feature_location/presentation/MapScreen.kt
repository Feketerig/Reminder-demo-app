package com.example.mobilecomputinghomework.feature_location.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.*
import com.google.maps.android.compose.clustering.Clustering

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val reminders by viewModel.reminders.collectAsState(initial = emptyList())
    val cameraPositionState = rememberCameraPositionState()
    val uiSettings by remember { mutableStateOf(MapUiSettings(mapToolbarEnabled = false)) }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
    ) {
        Clustering(
            items = reminders,
        )
        /*reminders.forEach {reminder ->
            val center = LatLng(reminder.lat!!, reminder.lon!!)
            Marker(
                state = MarkerState(position = center),
                title = "Chosen Location",

                )
            Circle(
                center = center,
                radius = reminder.radius!!.toDouble(),
                fillColor = Color(0x80CCCCCC),
                strokeColor = Color.LightGray
            )
        }*/
    }
}
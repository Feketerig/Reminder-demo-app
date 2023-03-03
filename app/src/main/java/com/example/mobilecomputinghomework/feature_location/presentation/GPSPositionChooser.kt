package com.example.mobilecomputinghomework.feature_location.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GPSPositionChooser(
    onClick: (LatLng, Int) -> Unit,
    onDismiss: () -> Unit,
    initialRadius: Float,
    initialMarker: LatLng?,
) {
    var radius by remember {
        mutableStateOf(initialRadius)
    }
    var markerPosition by remember{
        mutableStateOf(initialMarker)
    }
    val cameraPositionState = if (initialMarker != null){
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(initialMarker, 12f)
        }
    }else{
        rememberCameraPositionState()
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(mapToolbarEnabled = false)) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Choose a location!",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.height(450.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                GoogleMap(
                    modifier = Modifier.height(400.dp),
                    cameraPositionState = cameraPositionState,
                    uiSettings = uiSettings,
                    onMapClick = {
                        markerPosition = it
                    }
                ) {
                    markerPosition?.let {
                        Marker(
                            state = MarkerState(position = it),
                            title = "Chosen Location",
                        )
                        Circle(
                            center = it,
                            radius = radius.toDouble(),
                            fillColor = Color(0x80CCCCCC),
                            strokeColor = Color.LightGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(100.dp),
                        text = "Radius: ${radius.toInt()} m"
                    )
                    Slider(
                        modifier = Modifier.weight(1f),
                        value = radius,
                        onValueChange = { radius = it },
                        valueRange = 10f..1000f,
                    )

                }
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    onClick(markerPosition!!, radius.toInt())
                },
                enabled = markerPosition != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}
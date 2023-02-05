package com.example.mobilecomputinghomework.profile.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobilecomputinghomework.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    name: String,
) {
    var newName by remember{ mutableStateOf(name) }
    var enabled by remember { mutableStateOf(false) }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = selectedImageUri,
            fallback = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                Log.d("image", selectedImageUri.toString())
            },
        ) {
            Text(text = "Change profile picture")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (enabled){
                TextField(
                    value = newName,
                    onValueChange = {
                        newName = it
                    },
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }else{
                Text(text = newName, fontSize = 24.sp)
            }
            IconButton(onClick = { enabled = !enabled }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit name")
            }
        }

    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen("Levente Fazekas")
}
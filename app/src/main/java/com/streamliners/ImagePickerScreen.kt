package com.streamliners

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagePickerScreen(
    launchImagePicker: ((Uri) -> Unit) -> Unit
) {

    val pickedImageUri = remember {
        mutableStateOf<String?>(null)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        pickedImageUri.value?.let { uri ->
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = uri,
                contentDescription = "Selected image"
            )
        }

        Button(
            onClick = {
                launchImagePicker { uri ->
                    pickedImageUri.value = uri.toString()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Pick Image")
        }
    }
}
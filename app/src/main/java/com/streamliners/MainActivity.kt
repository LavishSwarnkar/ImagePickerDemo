package com.streamliners

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.dhaval2404.imagepicker.ImagePicker
import com.streamliners.ui.theme.ImagePickerDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ImagePickerDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ImagePickerScreen(launchImagePicker = ::launchImagePicker)
                }
            }
        }

        registerIntentLaunchers()
    }

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var imagePickerResultHandler: ((Uri) -> Unit)? = null

    private fun registerIntentLaunchers() {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data!!
                        imagePickerResultHandler?.invoke(fileUri)

                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun launchImagePicker(
        resultHandler: (Uri) -> Unit
    ) {
        imagePickerResultHandler = resultHandler

        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .createIntent { intent ->
                imagePickerLauncher.launch(intent)
            }
    }

}
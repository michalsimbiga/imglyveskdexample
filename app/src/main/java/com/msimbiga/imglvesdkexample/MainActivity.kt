package com.msimbiga.imglvesdkexample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ly.img.android.pesdk.VideoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.manager.SettingsList
import ly.img.android.pesdk.ui.activity.VideoEditorActivityResultContract

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                CreateMediaView()
            }
        }
    }
}


@Composable
fun CreateMediaView() {
    val context = LocalContext.current

    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var processedUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedUri = uri }
        }

    val videoEditor = rememberLauncherForActivityResult(
        contract = VideoEditorActivityResultContract(),
        onResult = { result ->
            when (result.resultStatus) {
                EditorSDKResult.Status.CANCELED -> Log.d("TAG", "Editor canceled")
                EditorSDKResult.Status.EXPORT_DONE -> processedUri = result.resultUri
                else -> Unit
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { launchVideoGallery(context, galleryLauncher) }) {
            Text("Select video to edit")
        }
        selectedUri?.let { uri ->
            Text("Selected uri $uri")
            Button(onClick = { launchEditFlow(videoEditor = videoEditor, uri = uri) }) {
                Text("Load to vesdk")
            }
        }
        processedUri?.let { uri ->
            Text(text = "Processed uri $uri")
            Button(onClick = {
                processedUri = null
                selectedUri = null
            }) {
                Text("Clear all")
            }
        }
    }
}

fun launchVideoGallery(
    context: Context,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    if (true == context.filesDir.listFiles()?.isNotEmpty()) {
        galleryLauncher.launch("video/*")
    }
}

fun launchEditFlow(
    videoEditor: ManagedActivityResultLauncher<SettingsList, EditorSDKResult>,
    uri: Uri,
) {
    val settingsList = VideoEditorSettingsList(false)
        .configure<LoadSettings> { it.source = uri }

    videoEditor.launch(settingsList)
    settingsList.release()
}

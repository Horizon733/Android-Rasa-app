package com.example.rasachatbotapp

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun OpenUrl(
    link: String,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarSection(username = "Webview", navigator = navigator)
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(link)
            }
        })
        BackHandler(
            enabled = true,
            onBack = {
                navigator.popBackStack()
                openUrl.value = false
            }
        )

    }

}
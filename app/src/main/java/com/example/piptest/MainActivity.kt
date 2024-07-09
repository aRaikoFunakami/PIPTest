package com.example.piptest

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.piptest.ui.theme.PIPTestTheme

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initWebView()
        setContent {
            PIPTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewContainer(Modifier.padding(innerPadding))
                }
            }
        }
        Log.d("PIPTest", "onCreate")
    }

    private fun initWebView() {
        webView = WebView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            val userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
            settings.userAgentString = userAgent
            loadUrl("https://radiko.jp/")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPipMode()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun enterPipMode() {
        val pipBuilder = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(16, 9))
            .build()
        enterPictureInPictureMode(pipBuilder)
    }

    @Composable
    fun WebViewContainer(modifier: Modifier = Modifier) {
        AndroidView(factory = { webView }, modifier = modifier)
    }
}
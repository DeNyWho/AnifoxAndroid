package club.anifox.android.feature.player.component.kodik

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import club.anifox.android.feature.player.BuildConfig

@Composable
internal fun WebPlayerKodikComponent(url: String) {
    val domainPlayerLink = "https://${BuildConfig.d}${BuildConfig.d_player_path}$url"
    val context = LocalContext.current
    var isPlayerReady by rememberSaveable { mutableStateOf(false) }

    val webView = WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            userAgentString = WebSettings.getDefaultUserAgent(context)
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            overScrollMode = View.OVER_SCROLL_NEVER
        }
        setBackgroundColor(Color.Transparent.value.toInt())
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.evaluateJavascript(
                    """
                    (function() {
                        return document.querySelector('iframe') !== null;
                    })();
                    """
                ) { result ->
                    if (result == "true") {
                        Handler(Looper.getMainLooper()).postDelayed({
                            isPlayerReady = true
                        }, 1000)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { webView },
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isPlayerReady) 1f else 0f),
            update = { view ->
                view.loadUrl(domainPlayerLink)
            }
        )
        if (!isPlayerReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
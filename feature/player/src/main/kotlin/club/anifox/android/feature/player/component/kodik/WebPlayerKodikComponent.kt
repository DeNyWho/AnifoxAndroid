package club.anifox.android.feature.player.component.kodik

import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import club.anifox.android.feature.player.BuildConfig

@Composable
internal fun WebPlayerKodikComponent(url: String) {
    val domainPlayerLink = "https://${BuildConfig.d}${BuildConfig.d_player_path}$url"

    val webView = WebView(LocalContext.current).apply {
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
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        AndroidView(
            factory = { webView },
            modifier = Modifier
                .fillMaxSize()
        ) { view ->
            view.loadUrl(domainPlayerLink)
        }
    }
}
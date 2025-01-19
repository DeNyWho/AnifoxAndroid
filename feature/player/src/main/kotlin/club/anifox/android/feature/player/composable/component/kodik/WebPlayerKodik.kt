package club.anifox.android.feature.player.composable.component.kodik

import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import club.anifox.android.feature.player.BuildConfig
import java.net.URLEncoder

@Composable
internal fun WebPlayerKodik(url: String) {
    val domainPlayerLink = "https://${BuildConfig.d}/"
    println("DOMAIN PLAYER LINK = $domainPlayerLink")

    val generatedLink = generateLink(url.drop(18), domainPlayerLink)
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
            userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            overScrollMode = View.OVER_SCROLL_NEVER
        }

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.let {
                    val headers = HashMap<String, String>()
                    headers["Referer"] = domainPlayerLink
                    view?.loadUrl(request.url.toString(), headers)
                }
                return true
            }
        }
    }

    println("DANKE SHON = ${generatedLink}")

    val html: String =
        """
            <!DOCTYPE html>
                <head>
                    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
                    <style>
                        html, body {
                            margin: 0;
                            padding: 0;
                            height: 100vh;
                            width: 100vw;
                            overflow: hidden;
                            position: fixed;
                        }
                        iframe {
                            width: 100vw;
                            height: 100vh;
                            border: none;
                            position: fixed;
                            top: 0;
                            left: 0;
                        }
                    </style>
                </head>
                <body>
                    <iframe id="kodik-player" src=${generatedLink} allowfullscreen allow="autoplay *; fullscreen *"></iframe>
                </body>
            </html>
        """.trimMargin()

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { webView },
            modifier = Modifier.fillMaxSize()
        ) { view ->
            view.loadDataWithBaseURL(domainPlayerLink, html, "text/html", "UTF-8", domainPlayerLink)
        }
    }
}

private fun generateLink(serialUrl: String, currentUrl: String): String {
    val regex = Regex("/serial/\\d+/[a-f0-9]+")
    val newSerialUrl = regex.replace(serialUrl) { matchResult ->
        val matchedText = matchResult.value
        val serialId = matchedText.split("/")[2]
        val serialKey = matchedText.split("/")[3]
        "/serial/$serialId/$serialKey"
    }

    val url = "https://${BuildConfig.pd}$newSerialUrl"
    val queryParams = mapOf(
        "d" to BuildConfig.d,
        "d_sign" to BuildConfig.d_sign,
        "pd" to BuildConfig.pd,
        "pd_sign" to BuildConfig.pd_sign,
        "ref" to currentUrl,
        "ref_sign" to BuildConfig.ref_sign,
        "bad_user" to "false",
        "cdn_is_working" to "true",
    )

    val queryString = queryParams.entries.joinToString("&") { (key, value) ->
        "$key=${value.encodeURIComponent()}"
    }

    return "$url?$queryString"
}

private fun String.encodeURIComponent(): String {
    return URLEncoder.encode(this, "UTF-8")
        .replace("+", "%20")
        .replace("%21", "!")
        .replace("%27", "'")
        .replace("%28", "(")
        .replace("%29", ")")
        .replace("%7E", "~")
}
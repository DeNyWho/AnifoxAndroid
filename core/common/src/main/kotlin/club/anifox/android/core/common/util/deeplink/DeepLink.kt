package club.anifox.android.core.common.util.deeplink

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeepLink @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun openYouTubeApp(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.setPackage("com.google.android.youtube")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(webIntent)
        }
    }

    fun openTelegram(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.setPackage("org.telegram.messenger")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(webIntent)
        }
    }

    fun openWeb(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(webIntent)
        }
    }
}
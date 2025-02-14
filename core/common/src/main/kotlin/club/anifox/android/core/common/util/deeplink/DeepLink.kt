package club.anifox.android.core.common.util.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeepLink @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun openYouTubeApp(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("com.google.android.youtube")

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            startActivity(context, intent, null)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(context, webIntent, null)
        }
    }

    fun openTelegram(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("org.telegram.messenger")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            startActivity(context, intent, null)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(context, webIntent, null)
        }
    }

    fun openWeb(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        intent.resolveActivity(context.packageManager)?.let {
            startActivity(context, intent, null)
        } ?: run {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(context, webIntent, null)
        }
    }
}
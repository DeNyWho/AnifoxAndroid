package club.anifox.android

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class AnifoxApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        initializeCrashlytics()
    }

    private fun initializeCrashlytics() {
        Firebase.crashlytics.isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .fetcherDispatcher(Dispatchers.IO.limitedParallelism(8))
            .decoderDispatcher(Dispatchers.IO.limitedParallelism(2))
            .build()
    }
}
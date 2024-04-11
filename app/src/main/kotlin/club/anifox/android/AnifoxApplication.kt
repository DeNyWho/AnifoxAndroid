package club.anifox.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
//class AnifoxApplication : Application(), ImageLoaderFactory {
//
//    @Inject
//    lateinit var imageLoader: dagger.Lazy<ImageLoader>
//
//    override fun newImageLoader(): ImageLoader = imageLoader.get()
//}

@HiltAndroidApp
class AnifoxApplication : Application()
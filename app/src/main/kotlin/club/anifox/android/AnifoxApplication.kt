package club.anifox.android

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

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
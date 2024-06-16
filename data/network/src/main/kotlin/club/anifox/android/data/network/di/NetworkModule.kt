package club.anifox.android.data.network.di

import club.anifox.android.data.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = false
    }

    @Provides
    @Singleton
    fun providesHttpClient(json: Json): HttpClient = HttpClient (OkHttp) {
        install(ContentNegotiation) {
            json(json)
        }
        defaultRequest {
            header("Content-Type", "application/json")
            url {
                protocol = URLProtocol.HTTPS
                host = BuildConfig.urlbase
                encodedPath = BuildConfig.urlpath
            }
        }
        install(HttpCookies)
        install(HttpCache)
        if(BuildConfig.DEBUG) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }

}
package club.anifox.android.data.network.interceptor

import club.anifox.android.data.datastore.source.UserSecurityDataSource
import club.anifox.android.data.network.BuildConfig
import club.anifox.android.data.network.api.ApiEndpoints
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.hc.core5.http.HttpHeaders

internal class AuthInterceptor(
    private val userSecurityDataSource: UserSecurityDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())

        if (response.code == 401) {
            response.close()

            val accessToken = runBlocking {
                userSecurityDataSource.accessToken.firstOrNull()
            }

            if (accessToken?.isNotEmpty() == true) {
                val newRequestWithAccessToken = chain.request().newBuilder()
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                    .build()

                response = chain.proceed(newRequestWithAccessToken)

                if (response.code == 401) {
                    response.close()

                    val refreshToken = runBlocking {
                        userSecurityDataSource.refreshToken.firstOrNull()
                    }

                    if (refreshToken?.isNotEmpty() == true) {
                        val tokenResponse = refreshToken(refreshToken)
                        if (tokenResponse.code == 200) {
                            response.close()
                            response = retryRequest(tokenResponse, chain)
                        }
                    }
                }
            }
        }

        return response
    }

    private fun refreshToken(refreshToken: String): Response {
        val client = OkHttpClient().newBuilder()
            .build()
        val newRequest = Request.Builder()
            .get()
            .url("https://${BuildConfig.urlbase}${BuildConfig.urlpath}${ApiEndpoints.AUTH}/${ApiEndpoints.REFRESH_TOKEN}?${ApiEndpoints.REFRESH_TOKEN}=$refreshToken")
            .build()
        return client.newCall(newRequest).execute()
    }

    private fun retryRequest(
        tokenResponse: Response,
        chain: Interceptor.Chain
    ): Response {
        val cookieHeader = tokenResponse.headers("Set-Cookie")

        var accessToken: String? = null
        var refreshToken: String? = null

        for (cookie in cookieHeader) {
            if (cookie.startsWith("access_token=")) {
                accessToken = cookie.substringAfter("access_token=").substringBefore(";")
            } else if (cookie.startsWith("refresh_token=")) {
                refreshToken = cookie.substringAfter("refresh_token=").substringBefore(";")
            }
        }

        if (accessToken != null && refreshToken != null) {
            runBlocking {
                userSecurityDataSource.saveAccessToken(accessToken)
                userSecurityDataSource.saveRefreshToken(refreshToken)

                val newRequestWithAccessToken = chain.request().newBuilder()
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
                    .build()

                return@runBlocking chain.proceed(newRequestWithAccessToken)
            }
        }

        return tokenResponse
    }
}
package club.anifox.android.data.network

import club.anifox.android.domain.model.common.request.ApiError
import club.anifox.android.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T : Any> safeApiCall(
    client: HttpClient,
    request: HttpRequestBuilder,
): Resource<T> {
    return try {
        val response: HttpResponse = client.request(request)
        when (response.status) {
            HttpStatusCode.OK, HttpStatusCode.Created -> {
                Resource.Success(data = response.body<T>())
            }

            else -> {
                Resource.Error(ApiError(response.status.value, response.bodyAsText()))
            }
        }
    } catch (e: Exception) {
        when (e) {
            is ClientRequestException -> {
                Resource.Error(ApiError(500, e.message))
            }

            else -> {
                Resource.Error(ApiError(500, "${e.message}"))
            }
        }
    }
}

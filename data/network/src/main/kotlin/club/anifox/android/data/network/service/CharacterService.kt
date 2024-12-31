package club.anifox.android.data.network.service

import club.anifox.android.data.network.api.ApiEndpoints
import club.anifox.android.data.network.models.dto.character.full.CharacterFullDTO
import club.anifox.android.data.network.safeApiCall
import club.anifox.android.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.encodedPath
import javax.inject.Inject

class CharacterService @Inject constructor (private val client: HttpClient) {
    suspend fun getCharacterFull(id: String): Resource<CharacterFullDTO> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME_CHARACTERS}/$id"
            }
        }

        return safeApiCall<CharacterFullDTO>(client, request)
    }
}
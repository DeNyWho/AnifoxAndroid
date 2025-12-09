package su.anifox.data.network.di

import com.apollographql.apollo.ApolloClient
import org.koin.dsl.module
import su.anifox.data.network.BuildKonfig

val networkModule = module {
    single<ApolloClient> {
        ApolloClient.Builder()
            .serverUrl(BuildKonfig.BASE_URL)
            .build()
    }
}
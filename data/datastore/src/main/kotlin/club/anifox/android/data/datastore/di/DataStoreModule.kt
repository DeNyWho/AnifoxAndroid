package club.anifox.android.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import club.anifox.android.data.datastore.serializer.UserSecurityDataStorageSerializer
import club.anifox.android.data.datastore.source.UserSecurityDataSource
import club.anifox.android.domain.model.user.UserSecurityData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserSecurityDataStorageSerializer(): UserSecurityDataStorageSerializer {
        return UserSecurityDataStorageSerializer()
    }

    @Provides
    @Singleton
    fun provideUserSecurityDataSource(dataStore: DataStore<UserSecurityData>): UserSecurityDataSource {
        return UserSecurityDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserSecurityDataStore(
        localStorageSerializer: UserSecurityDataStorageSerializer,
        context: Context
    ): DataStore<UserSecurityData> {
        return DataStoreFactory.create(
            serializer = localStorageSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations = listOf()
        ) {
            context.dataStoreFile("user_security_data.pb")
        }
    }
}
package club.anifox.android.data.local.di

import android.content.Context
import androidx.room.Room
import club.anifox.android.data.local.AnifoxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAnifoxDatabase(
        @ApplicationContext context: Context,
    ): AnifoxDatabase = Room.databaseBuilder(
        context,
        AnifoxDatabase::class.java,
        "anifox-database",
    ).build()
}

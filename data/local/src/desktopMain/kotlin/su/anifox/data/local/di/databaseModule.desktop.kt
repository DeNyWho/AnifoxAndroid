package su.anifox.data.local.di

import org.koin.core.module.Module
import org.koin.dsl.module
import su.anifox.data.local.DataBaseFactory
import su.anifox.data.local.datasource.LocalDatasource

actual val databaseModule: Module = module {
    factory { DataBaseFactory() }
    single<LocalDatasource> { LocalDatasource.create(dataBaseFactory = get()) }
}
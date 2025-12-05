package su.anifox.data.local.datasource

import su.anifox.data.local.DataBaseFactory

interface LocalDatasource {

    companion object {
        fun create(dataBaseFactory: DataBaseFactory): LocalDatasource {
            return LocalDatasourceImpl()
        }
    }
}

internal class LocalDatasourceImpl() : LocalDatasource {

}
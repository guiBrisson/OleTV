package br.tv.ole.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import br.tv.ole.db.OleTvDb
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDbModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = OleTvDb.Schema,
            context = get(),
            name = "oleTvDb",
        )
    }
}

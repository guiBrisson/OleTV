package br.tv.ole.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import br.tv.ole.db.OleTvDb
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDbModule: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = OleTvDb.Schema,
            name = "KampkitDb",
        )
    }
}
package br.tv.ole

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import br.tv.ole.db.OleTvDb

internal actual fun testDbDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    .also { OleTvDb.Schema.create(it) }

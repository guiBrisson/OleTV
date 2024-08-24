package br.tv.ole

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import br.tv.ole.db.OleTvDb

internal actual fun testDbDriver(): SqlDriver = inMemoryDriver(OleTvDb.Schema)

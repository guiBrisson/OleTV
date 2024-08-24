package br.tv.ole.di

import br.tv.ole.Database
import br.tv.ole.service.PlaceholderService
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module


val dbModule = module {
    includes(platformDbModule)

    single<Database> {
        Database(
            sqlDriver = get(),
            dispatcher = Dispatchers.Default,
        )
    }

    single<PlaceholderService> {
        val db: Database = get()
        db.placeholderTable
    }
}

expect val platformDbModule: Module

package br.tv.ole.di

import br.tv.ole.Database
import br.tv.ole.service.ChannelService
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module


val dbModule = module {
    includes(platformDbModule)

    single<Database> {
        Database(
            sqlDriver = get(),
            dispatcher = Dispatchers.Default, // TODO: change to IO dispatcher
        )
    }

    single<ChannelService> {
        fromDatabase().channelService
    }
}

expect val platformDbModule: Module

// Helper function that provides easy access for Database
fun Scope.fromDatabase(): Database = get()

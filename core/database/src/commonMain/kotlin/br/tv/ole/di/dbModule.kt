package br.tv.ole.di

import br.tv.ole.Database
import br.tv.ole.service.ChannelService
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module


val dbModule = module {
    includes(platformDbModule)

    single<Database> {
        Database(
            sqlDriver = get(),
            log = getWith("Database"),
            dispatcher = Dispatchers.Default, // TODO: change to IO dispatcher
        )
    }

    single<ChannelService> {
        fromDatabase().channelService
    }

    // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
    // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
    // that would likely go into platformModule expect/actual.
    // See https://github.com/touchlab/Kermit
    val baseLogger =
        Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), tag = "OleTV")
    factory { (tag: String?) ->
        if (tag != null) baseLogger.withTag(tag) else baseLogger
    }
}

expect val platformDbModule: Module

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Helper function that provides easy access for Database
fun Scope.fromDatabase(): Database = get()

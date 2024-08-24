package br.tv.ole

import br.tv.ole.di.dbModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication = startKoin {
    modules(
        appModule,
        coreModule,
    )
}

private val coreModule = module {
    includes(dbModule)
}
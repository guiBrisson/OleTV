package br.tv.ole

import android.app.Application
import android.content.Context
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@App }
            }
        )
    }
}

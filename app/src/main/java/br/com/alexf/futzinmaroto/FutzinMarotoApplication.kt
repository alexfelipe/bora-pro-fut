package br.com.alexf.futzinmaroto

import android.app.Application
import br.com.alexf.futzinmaroto.di.appModule
import br.com.alexf.futzinmaroto.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FutzinMarotoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FutzinMarotoApplication)
            modules(
                appModule,
                dataModule
            )
        }
    }

}
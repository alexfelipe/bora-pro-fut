package br.com.alexf.boraprofut

import android.app.Application
import br.com.alexf.boraprofut.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BoraProFutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BoraProFutApplication)
            modules(appModule)
        }
    }

}
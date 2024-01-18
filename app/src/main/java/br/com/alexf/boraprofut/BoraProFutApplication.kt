package br.com.alexf.boraprofut

import android.app.Application
import br.com.alexf.boraprofut.features.gameScreenModule
import org.koin.core.context.startKoin

class BoraProFutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(gameScreenModule)
        }
    }

}
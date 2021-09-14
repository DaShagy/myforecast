package com.example.myforecast

import android.app.Application
import com.example.di.repositoriesModule
import com.example.di.useCasesModule
import com.example.myforecast.di.viewModelModules

import io.realm.Realm
import org.koin.core.context.startKoin

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        startKoin {
            modules(listOf(repositoriesModule, viewModelModules, useCasesModule))
        }
    }
}

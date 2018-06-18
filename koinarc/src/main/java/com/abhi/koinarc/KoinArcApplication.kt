package com.abhi.koinarc

import android.app.Application
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

class KoinArcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(this, listOf(myModule))
    }
}

// Koin module
val myModule: Module = applicationContext {
    factory { SimplePresenter(get()) } // get() will resolve Repository instance
    viewModel { SimpleViewModel(get()) } // get() will resolve Repository instance
    bean { MyRepository() as Repository }
}
package com.abhi.spectacle

import android.app.Application
import android.util.Log
import com.akaita.java.rxjava2debug.RxJava2Debug
import com.facebook.soloader.SoLoader
import com.facebook.sonar.android.AndroidSonarClient
import com.facebook.sonar.android.utils.SonarUtils
import com.facebook.sonar.plugins.inspector.DescriptorMapping
import com.facebook.sonar.plugins.inspector.InspectorSonarPlugin
import com.facebook.sonar.plugins.network.NetworkSonarPlugin


class SpectacleApplication : Application() {

    val networkSonarPlugin = NetworkSonarPlugin()

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && SonarUtils.shouldEnableSonar(this)) {
            val client = AndroidSonarClient.getInstance(this)
            client.addPlugin(networkSonarPlugin)
            client.addPlugin(InspectorSonarPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        } else Log.e(SpectacleApplication::class.simpleName, "Sonar not enabled")

        RxJava2Debug.enableRxJava2AssemblyTracking()
    }
}
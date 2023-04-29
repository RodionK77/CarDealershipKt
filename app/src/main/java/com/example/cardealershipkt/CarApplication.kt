package com.example.cardealershipkt

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class CarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CarDatabase.initialize(this)

        MapKitFactory.setApiKey("5c9eada9-3c5c-48e2-a3b4-61e48079b3bb")
    }
}
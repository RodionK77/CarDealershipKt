package com.example.cardealershipkt

import android.app.Application

class CarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CarDatabase.initialize(this)
    }
}
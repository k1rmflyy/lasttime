package com.gorbkostya.lasttime.presentation

import android.app.Application
import com.gorbkostya.lasttime.data.AppContainer

class LastTimeApp: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
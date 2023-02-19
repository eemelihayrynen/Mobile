package com.example.mobile

import android.app.Application

/**
 * This application class sets up our dependency [Graph] with a context
 */
class Applikaatio : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
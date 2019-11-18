package com.alesno.testtaskispring.app

import android.app.Application

class TestTaskApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
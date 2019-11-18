package com.alesno.testtaskispring.app

import android.app.Application
import com.alesno.testtaskispring.model.objectbox.ObjectBox

class TestTaskApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
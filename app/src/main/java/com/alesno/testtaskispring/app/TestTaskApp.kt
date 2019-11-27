package com.alesno.testtaskispring.app

import android.app.Application
import com.alesno.testtaskispring.model.objectbox.ObjectBox
import com.alesno.testtaskispring.model.objectbox.dao.VideosDaoImpl
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.repository.RepositoryImpl
import com.alesno.testtaskispring.model.service.ApiService
import io.objectbox.Box

class TestTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        initObjects()
    }

    private fun initObjects() {
        val apiService = ApiService.create()
        val videosBox: Box<VideoObject> = ObjectBox.boxStore.boxFor(VideoObject::class.java)
        val videosDao = VideosDaoImpl(videosBox)
        RepositoryImpl.RepositoryProvider.init(apiService, videosDao)
    }

}
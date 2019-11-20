package com.alesno.testtaskispring.ui.videoactivity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer

class VideoViewModel(val videosDao: VideosDao, val objectTransformer: ObjectTransformer): ViewModel() {

    var topics: ObservableList<String> = ObservableArrayList<String>()

    fun onViewCreated() {
        val listTopics = mutableListOf<String>()
        listTopics.add("Здоровье")
        listTopics.add("Учеба")
        topics.addAll(listTopics)
    }
}
package com.alesno.testtaskispring.ui.videoactivity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer

class VideoViewModel(val videosDao: VideosDao, val objectTransformer: ObjectTransformer): ViewModel() {

    var topics: ObservableList<String> = ObservableArrayList<String>()
    var experts: ObservableList<ExpertObject> = ObservableArrayList<ExpertObject>()

    fun onViewCreated() {
        val listTopics = mutableListOf<String>()
        listTopics.add("Здоровье")
        listTopics.add("Учеба")
        listTopics.add("Политика")
        listTopics.add("Безопасность")
        listTopics.add("Охрана труда")
        topics.addAll(listTopics)

        experts.add(ExpertObject())
        experts.add(ExpertObject())
    }
}
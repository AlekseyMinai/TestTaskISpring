package com.alesno.testtaskispring.model.objectbox.dao

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import io.objectbox.Box

class VideosDaoImpl(private val videosBox: Box<VideoObject>): VideosDao {

    override fun getAllVideos(): List<VideoObject> {
        return videosBox.all
    }

    override fun insertAllVideos(videos: List<VideoObject>) {
        videosBox.put(videos)
    }
}
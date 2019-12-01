package com.alesno.testtaskispring.model.objectbox.dao

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import io.objectbox.Box

class VideosDaoImpl(private val videosBox: Box<VideoObject>) : VideosDao {
    override fun updateVideo(videoObject: VideoObject) {
        videosBox.put(videoObject)
    }

    override fun getVideoById(idVideo: Long): VideoObject {
        return videosBox.get(idVideo)
    }

    override fun getAllVideos(): List<VideoObject> {
        return videosBox.all
    }

    override fun insertAllVideos(videos: List<VideoObject>): Boolean {
        val set: MutableSet<VideoObject> = videosBox.all.toHashSet()
        var countOfInsert = 0
        for (video in videos) {
            if (set.add(video)) {
                videosBox.put(video)
                countOfInsert++
            }
        }
        return countOfInsert == 0
    }
}
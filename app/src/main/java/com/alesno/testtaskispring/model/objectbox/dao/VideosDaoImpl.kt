package com.alesno.testtaskispring.model.objectbox.dao

import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import io.objectbox.Box

class VideosDaoImpl(private val videosBox: Box<VideoObject>) : VideosDao {
    override fun updateVideo(videoObject: VideoObject?) {
        videoObject?.let { videosBox.put(videoObject) }
    }

    override fun getVideoById(idVideo: Long): VideoObject {
        return videosBox.get(idVideo)
    }

    override fun getAllVideos(): List<VideoObject> {
        return videosBox.all
    }

    override fun insertAllVideos(videos: List<VideoObject>) {
        val set: MutableSet<VideoObject> = videosBox.all.toHashSet()
        for (video in videos) {
            if (set.add(video)) {
                videosBox.put(video)
            }
        }
    }
}
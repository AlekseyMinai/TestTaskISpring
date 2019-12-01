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

    override fun insertAllVideos(videosFromServer: List<VideoObject>): Boolean {
        val videosFromDb = videosBox.all
        val setVideosFromDb: MutableSet<VideoObject> = videosFromDb.toHashSet()
        var countOfInsert = 0
        for (video in videosFromServer) {
            if (setVideosFromDb.add(video)) {
                videosBox.put(video)
                countOfInsert++
            }
        }
        deleteOldNotFavoriteVideos(videosFromServer, setVideosFromDb)
        return countOfInsert == 0
    }

    private fun deleteOldNotFavoriteVideos(
        videosFromServer: List<VideoObject>,
        setVideosFromDb: MutableSet<VideoObject>
    ) {
        for (video in videosFromServer) {
            if (!setVideosFromDb.add(video)) {
                setVideosFromDb.remove(video)
            }
        }
        for (video in setVideosFromDb) {
            if (!video.isFavorite) {
                videosBox.remove(video)
            }
        }
    }
}
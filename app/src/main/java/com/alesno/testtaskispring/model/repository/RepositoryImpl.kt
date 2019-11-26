package com.alesno.testtaskispring.model.repository

import android.util.Log
import com.alesno.testtaskispring.common.LOG
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.response.Response
import com.alesno.testtaskispring.model.service.ApiService
import kotlinx.coroutines.*

class RepositoryImpl(
    private val mService: ApiService,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer
) : Repository {

    private val videosObj: MutableList<VideoObject> = mutableListOf()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override suspend fun getListVideosObject(scope: CoroutineScope): List<VideoObject> {
        if (videosObj.isNotEmpty()) {
            return videosObj
        }
        val job = scope.launch(Dispatchers.IO) {
            putVideosObjFromDbInList()
            if (videosObj.isNotEmpty()) {
                return@launch
            }
            updateDataInDB()
        }
        job.join()
        return videosObj
    }

    override suspend fun getListVideosObjFromDb(scope: CoroutineScope): MutableList<VideoObject> {
        val job = scope.launch { putVideosObjFromDbInList() }
        job.join()
        return videosObj
    }

    override suspend fun updateListFromServer(): List<VideoObject> {
        updateDataInDB()
        return videosObj
    }

    override fun getVideoById(videoId: Long): VideoObject {
        return videosDao.getVideoById(videoId)
    }

    override fun updateVideo(videoObj: VideoObject) {
        videosDao.updateVideo(videoObj)
    }

    override fun changeFavoriteStatus(
        idVideo: Long,
        isFavorite: Boolean
    ): MutableList<VideoObject> {
        scope.launch(Dispatchers.IO) {
            putVideosObjFromDbInList()
            val videoObj: VideoObject = getVideoByIdFromCache(idVideo) ?: return@launch
            videoObj.isFavorite = isFavorite
            videosDao.updateVideo(videoObj)
            putVideosObjFromDbInList()
        }

        return videosObj
    }

    private suspend fun getResponseAsync(): Response {
        //redo it!! with sealed classes??
        try {
            return mService.getResponseAsync().await()
        } catch (e: Exception) {
            Log.d(LOG, "exception = ${e.message.toString()}")
        }

        return Response(listOf())
    }

    private fun getAllVideosFromDbAsync(): Deferred<List<VideoObject>> {
        return scope.async { videosDao.getAllVideos() }
    }

    private suspend fun putVideosObjFromDbInList() {
        val listVideosObj = getAllVideosFromDbAsync().await()
        videosObj.clear()
        videosObj.addAll(sortByTitle(listVideosObj))
    }

    private suspend fun updateDataInDB() {
        val response = getResponseAsync()
        videosDao.insertAllVideos(objectTransformer.responseTransformer(response))
        putVideosObjFromDbInList()
    }

    private fun getVideoByIdFromCache(idVideo: Long): VideoObject? {
        var videoObj: VideoObject? = null
        videosObj.forEach { videoObject ->
            if (videoObject.id == idVideo) videoObj = videoObject
        }
        return videoObj
    }

    private fun sortByTitle(videosObj: List<VideoObject>): List<VideoObject> {
        return videosObj.sortedWith(compareBy { it.title })
    }

    override fun filterByFavoriteVideos(): List<VideoObject> {
        return videosObj.filter { videoObject -> videoObject.isFavorite }
    }

}
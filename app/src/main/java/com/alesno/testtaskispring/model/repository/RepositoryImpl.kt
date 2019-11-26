package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.response.ResponseJson
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
            updateCacheVideoObjectFromDb()
            if (videosObj.isNotEmpty()) {
                return@launch
            }
            updateListFromServer()
        }
        job.join()
        return videosObj
    }

    override suspend fun getListVideosObjFromDb(scope: CoroutineScope): MutableList<VideoObject> {
        val job = scope.launch { updateCacheVideoObjectFromDb() }
        job.join()
        return videosObj
    }

    override suspend fun updateListFromServer(): List<VideoObject> {
        //redo it with seated class!!
        try {
            val response = getResponseAsync().await()
            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))
            updateCacheVideoObjectFromDb()
        } catch (e: Exception) {

        }
        return videosObj
    }

    override fun getVideoById(videoId: Long): VideoObject {
        return videosDao.getVideoById(videoId)
    }

    override fun updateVideo(videoObj: VideoObject) {
        videosDao.updateVideo(videoObj)
    }

    private suspend fun updateCacheVideoObjectFromDb() {
        val listVideosObj = getAllVideosFromDbAsync().await()
        videosObj.clear()
        videosObj.addAll(sortByTitle(listVideosObj))
    }

    override suspend fun changeFavoriteStatus(
        idVideo: Long,
        isFavorite: Boolean
    ): MutableList<VideoObject> {

        updateCacheVideoObjectFromDb()
        val videoObj: VideoObject = findVideoById(videosObj, idVideo)
        videoObj.isFavorite = isFavorite
        videosDao.updateVideo(videoObj)
        updateCacheVideoObjectFromDb()
        return videosObj
    }

    private fun getResponseAsync(): Deferred<ResponseJson> {
        return mService.getResponseAsync()
    }

    private fun getAllVideosFromDbAsync(): Deferred<List<VideoObject>> {
        return scope.async { videosDao.getAllVideos() }
    }

}
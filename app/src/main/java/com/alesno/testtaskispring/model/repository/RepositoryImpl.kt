package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.transformer.base.fromListDataToDomain
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import com.alesno.testtaskispring.model.response.ResponseJson
import com.alesno.testtaskispring.model.service.ApiService
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class RepositoryImpl(
    private val mService: ApiService,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer,
    private val mScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
    private val mCoroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
    private val mVideosObj: MutableList<VideoObject> = mutableListOf()
) : Repository {

    override suspend fun getListVideosObject(): List<VideoObject> =
        withContext(mCoroutineContext) {
            if (mVideosObj.isNotEmpty()) {
                return@withContext mVideosObj
            }
            updateCacheVideoObjectFromDb()
            if (mVideosObj.isNotEmpty()) {
                return@withContext mVideosObj
            }
            updateListFromServer()
            return@withContext mVideosObj
        }


    override suspend fun getListVideosObjFromDb(): List<VideoObject> =
        withContext(mCoroutineContext) {
            updateCacheVideoObjectFromDb()
            return@withContext mVideosObj
        }

    override suspend fun updateListFromServer(): List<VideoObject> {
        //redo it with sealed class!!
        try {
            val response = getResponseAsync().await()
            insertAllVideosInDb(response)
            updateCacheVideoObjectFromDb()
        } catch (e: Exception) {

        }
        return mVideosObj
    }

    override suspend fun getVideoById(videoId: Long): VideoObject =
        withContext(mCoroutineContext) {
            val videoObj = videosDao.getVideoById(videoId)
            videoObj
        }


    override suspend fun updateVideo(videoObj: VideoObject) {
        withContext(mCoroutineContext) {
            videosDao.updateVideo(videoObj)
        }
    }

    override suspend fun changeFavoriteStatus(
        idVideo: Long,
        isFavorite: Boolean
    ): List<VideoObject> = withContext(mCoroutineContext) {
        updateCacheVideoObjectFromDb()
        val videoObj: VideoObject? = findVideoById(mVideosObj, idVideo)
        videoObj?.isFavorite = isFavorite
        videoObj?.let { updateVideo(it) }
        updateCacheVideoObjectFromDb()
        return@withContext mVideosObj
    }

    private suspend fun insertAllVideosInDb(response: ResponseJson) {
        withContext(mCoroutineContext) {
            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))
        }
    }

    private suspend fun updateCacheVideoObjectFromDb() {
        val listVideosObj = getAllVideosFromDbAsync().await()
        mVideosObj.clear()
        mVideosObj.addAll(sortByTitle(listVideosObj))
    }

    private fun getAllVideosFromDbAsync(): Deferred<List<VideoObject>> {
        return mScope.async { videosDao.getAllVideos() }
    }

    private fun getResponseAsync(): Deferred<ResponseJson> {
        return mService.getResponseAsync()
    }

    object RepositoryProvider {

        private lateinit var repository: Repository

        fun init(apiService: ApiService, videosDao: VideosDao) {
            repository = RepositoryImpl(apiService, videosDao, ObjectTransformerImpl)
        }

        fun getRepositoryIml(): Repository {
            return repository
        }
    }

}
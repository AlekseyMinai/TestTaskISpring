package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.domain.transformer.fromDataToDomainDetail
import com.alesno.testtaskispring.model.domain.transformer.fromDomainToDataDetail
import com.alesno.testtaskispring.model.domain.transformer.transformToListVideosCommonDomain
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

    override suspend fun getListVideos(): List<VideoCommonDomain> =
        withContext(mCoroutineContext) {
            if (mVideosObj.isNotEmpty()) {
                return@withContext mVideosObj
            }
            updateCacheVideoObjectFromDb()
            if (mVideosObj.isNotEmpty()) {
                return@withContext mVideosObj
            }
            getListVideoFromServer()
            return@withContext mVideosObj
        }.transformToListVideosCommonDomain()

    override fun getListFavoriteVideos(): List<VideoCommonDomain> {
        return filterByFavoriteVideos(mVideosObj).transformToListVideosCommonDomain()
    }


    override suspend fun getListVideosFromDb(): List<VideoCommonDomain> =
        withContext(mCoroutineContext) {
            updateCacheVideoObjectFromDb()
            return@withContext mVideosObj
        }.transformToListVideosCommonDomain()

    override suspend fun getListVideoFromServer(): List<VideoCommonDomain> {
        updateVideosObjectFromServer()
        return mVideosObj.transformToListVideosCommonDomain()
    }

    override suspend fun changeFavoriteStatus(
        idVideo: Long,
        isFavorite: Boolean
    ): List<VideoCommonDomain> = withContext(mCoroutineContext) {
        updateCacheVideoObjectFromDb()
        val videoObj: VideoObject? = findVideoById(mVideosObj, idVideo)
        videoObj?.isFavorite = isFavorite
        videoObj?.let { updateVideo(it) }
        updateCacheVideoObjectFromDb()
        return@withContext mVideosObj
    }.transformToListVideosCommonDomain()

    override suspend fun getVideoById(videoId: Long): VideoDetailVMDomain =
        withContext(mCoroutineContext) {
            val videoObj = videosDao.getVideoById(videoId)
            fromDataToDomainDetail(videoObj)
        }


    override suspend fun updateVideo(videoDomain: VideoDetailVMDomain) {
        val changingVideoObj = findVideoById(mVideosObj, videoDomain.id)
        changingVideoObj?.let {
            updateVideo(fromDomainToDataDetail(videoDomain, changingVideoObj))
        }
    }

    private suspend fun updateVideo(videoObject: VideoObject) {
        withContext(mCoroutineContext) {
            videosDao.updateVideo(videoObject)
        }
    }

    private suspend fun insertAllVideosInDb(response: ResponseJson) {
        withContext(mCoroutineContext) {
            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))
        }
    }

    private suspend fun updateVideosObjectFromServer(): List<VideoObject> {

        //redo it with sealed class!!
        try {
            val response = getResponseAsync().await()
            insertAllVideosInDb(response)
            updateCacheVideoObjectFromDb()
        } catch (e: Exception) {

        }
        return mVideosObj
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
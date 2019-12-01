package com.alesno.testtaskispring.model.repository

import android.util.Log
import com.alesno.testtaskispring.common.LOG
import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.domain.transformer.fromDataToDomainDetail
import com.alesno.testtaskispring.model.domain.transformer.fromDomainToDataDetail
import com.alesno.testtaskispring.model.domain.transformer.toListResult
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import com.alesno.testtaskispring.model.response.ResponseJson
import com.alesno.testtaskispring.model.service.ApiService
import com.alesno.testtaskispring.model.service.Results
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class RepositoryImpl(
    private val mService: ApiService,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer,
    private val mCoroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
    private val mVideosObj: MutableList<VideoObject> = mutableListOf()
) : Repository {

    var isFailConnection = false

    override suspend fun getListVideos(): ListResult =
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
        }.toListResult(isFailConnection)

    override fun getListFavoriteVideos(): ListResult {
        return filterByFavoriteVideos(mVideosObj).toListResult()
    }

    override suspend fun getListVideosFromDb(): ListResult =
        withContext(mCoroutineContext) {
            updateCacheVideoObjectFromDb()
            return@withContext mVideosObj
        }.toListResult()

    override suspend fun getListVideoFromServer(): ListResult {
        updateVideosObjectFromServer()
        return mVideosObj.toListResult(isFailConnection)
    }

    override suspend fun changeFavoriteStatus(
        idVideo: Long,
        isFavorite: Boolean
    ): ListResult = withContext(mCoroutineContext) {
        updateCacheVideoObjectFromDb()
        val videoObj: VideoObject? = findVideoById(mVideosObj, idVideo)
        videoObj?.isFavorite = isFavorite
        videoObj?.let { updateVideo(it) }
        updateCacheVideoObjectFromDb()
        return@withContext mVideosObj
    }.toListResult()

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
        when (val result = getResponseOrError()) {
            is Results.Success<ResponseJson> -> {
                insertAllVideosInDb(result.successResult)
                updateCacheVideoObjectFromDb()
                isFailConnection = false
            }
            is Results.Error -> {
                Log.e(LOG, result.message)
                isFailConnection = true
            }
        }
        return mVideosObj
    }

    private suspend fun getResponseOrError(): Results<ResponseJson> {
        return try {
            Results.Success(mService.getResponseAsync())
        } catch (e: Exception) {
            Results.Error(e.message.toString())
        }
    }

    private suspend fun updateCacheVideoObjectFromDb() {
        withContext(mCoroutineContext) {
            val listVideosObj = videosDao.getAllVideos()
            mVideosObj.clear()
            mVideosObj.addAll(sortByTitle(listVideosObj))
        }
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
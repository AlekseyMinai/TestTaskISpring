package com.alesno.testtaskispring.model.repository

import com.alesno.testtaskispring.model.domain.VideoDetailVMDomain
import com.alesno.testtaskispring.model.domain.transformer.fromDataToDomainDetail
import com.alesno.testtaskispring.model.domain.transformer.fromDomainToDataDetail
import com.alesno.testtaskispring.model.domain.transformer.toCommonDomainList
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
    private val service: ApiService,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer,
    private val videosObj: MutableList<VideoObject> = mutableListOf(),
    private val coroutineContext: ExecutorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
) : Repository {

    override suspend fun getListVideos(): ListResult {
        if (!videosObj.isNullOrEmpty()) {
            return ListResult.NewData(videosObj.toCommonDomainList())
        }
        val listVideoFromDb = getListVideosFromDb()
        if (!listVideoFromDb.isNullOrEmpty()) {
            return ListResult.NewData(listVideoFromDb.toCommonDomainList())
        }
        return updateVideosObjectFromServer()
    }

    override fun getListFavoriteVideos(): ListResult {
        return ListResult.Favorite(videosObj.filterByFavoriteVideos().toCommonDomainList())
    }

    override suspend fun getListVideoFromServer(): ListResult {
        return updateVideosObjectFromServer()
    }

    override suspend fun changeFavoriteStatus(idVideo: Long, isFavorite: Boolean): ListResult =
        withContext(coroutineContext) {
            val videoObj: VideoObject? = findVideoById(getListVideosFromDb(), idVideo)
            videoObj?.isFavorite = isFavorite
            videoObj?.let { updateVideoInDb(it) }
            ListResult.NewData(getListVideosFromDb().toCommonDomainList())
        }

    override suspend fun getListResultFromDb(): ListResult {
        return ListResult.NewData(getListVideosFromDb().toCommonDomainList())
    }

    override suspend fun getVideoById(videoId: Long): VideoDetailVMDomain =
        withContext(coroutineContext) {
            val videoObj = videosDao.getVideoById(videoId)
            fromDataToDomainDetail(videoObj)
        }

    override suspend fun updateVideo(videoDomain: VideoDetailVMDomain) {
        val changingVideoObj = findVideoById(videosObj, videoDomain.id)
        changingVideoObj?.let {
            updateVideoInDb(fromDomainToDataDetail(videoDomain, changingVideoObj))
        }
    }

    private suspend fun getResponseOrError(): Results<ResponseJson> {
        return try {
            Results.Success(service.getResponseAsync())
        } catch (e: Exception) {
            Results.Error(e.message.toString())
        }
    }

    private suspend fun updateVideosObjectFromServer(): ListResult =
        when (val result = getResponseOrError()) {
            is Results.Success<ResponseJson> -> {
                val hasNewData = insertAllVideosInDbIfHasNew(result.successResult)
                if (hasNewData) {
                    ListResult.NewData(getListVideosFromDb().toCommonDomainList())
                } else {
                    ListResult.OldData(videosObj.toCommonDomainList())
                }
            }
            is Results.Error -> {
                ListResult.ConnectError
            }
        }

    private suspend fun getListVideosFromDb(): List<VideoObject> =
        withContext(coroutineContext) {
            val videosObj = videosDao.getAllVideos().sortByTitle()
            updateVideosObj(videosObj)
            videosObj
        }

    private suspend fun insertAllVideosInDbIfHasNew(response: ResponseJson): Boolean =
        withContext(coroutineContext) {
            videosDao.insertAllVideos(objectTransformer.responseTransformer(response))
        }

    private fun updateVideosObj(newListVideoObj: List<VideoObject>) {
        videosObj.clear()
        videosObj.addAll(newListVideoObj)
    }

    private suspend fun updateVideoInDb(videoObject: VideoObject) {
        withContext(coroutineContext) {
            videosDao.updateVideo(videoObject)
        }
    }

    object RepositoryProvider {

        private lateinit var repository: Repository

        fun init(apiService: ApiService, videosDao: VideosDao) {
            repository = RepositoryImpl(apiService, videosDao, ObjectTransformerImpl)
        }

        fun getRepositoryImpl(): Repository {
            return repository
        }
    }

}
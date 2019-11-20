package com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.repository.Repository

class CommonViewModelFactory(
    private val repository: Repository,
    private val videosDao: VideosDao,
    private val objectTransformer: ObjectTransformer
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass == CommonViewModel::class.java){
            CommonViewModel(
                repository,
                videosDao,
                objectTransformer
            ) as T
        }else {
            return throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
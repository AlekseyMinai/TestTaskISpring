package com.alesno.testtaskispring.ui.videoactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer

class VideoViewModelFactory(private val videosDao: VideosDao,
                            private val objectTransformer: ObjectTransformer
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass == VideoViewModel::class.java){
            VideoViewModel(
                videosDao,
                objectTransformer
            ) as T
        }else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
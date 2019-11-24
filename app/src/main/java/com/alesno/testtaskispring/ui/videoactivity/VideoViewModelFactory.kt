package com.alesno.testtaskispring.ui.videoactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alesno.testtaskispring.model.objectbox.dao.VideosDao
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformer
import com.alesno.testtaskispring.model.repository.Repository

class VideoViewModelFactory(
    private val mRepository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == VideoViewModel::class.java) {
            VideoViewModel(mRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
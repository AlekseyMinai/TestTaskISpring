package com.alesno.testtaskispring.ui.recyclerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alesno.testtaskispring.model.repository.Repository
import com.alesno.testtaskispring.ui.CommonViewModel

class CommonViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass == CommonViewModel::class.java){
            CommonViewModel(repository) as T
        }else {
            return throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
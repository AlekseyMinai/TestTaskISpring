package com.alesno.testtaskispring.app

import android.content.Context
import com.alesno.testtaskispring.model.objectbox.entities.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {

    lateinit var boxStore: BoxStore
    private set

    fun init(context: Context){
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

    }
}
package com.alesno.testtaskispring.ui.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject


@BindingAdapter("setItems")
fun setItem(recyclerView: RecyclerView, videos: List<VideoObject>){
    (recyclerView.adapter as VideoAdapter).replaceVideo(videos)
}

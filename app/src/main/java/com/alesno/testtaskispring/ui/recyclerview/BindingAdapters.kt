package com.alesno.testtaskispring.ui.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.model.objectbox.entity.VideoObj


@BindingAdapter("setItems")
fun setItem(recyclerView: RecyclerView, videos: List<VideoObj>){
    (recyclerView.adapter as VideoAdapter).replaceVideo(videos)
}

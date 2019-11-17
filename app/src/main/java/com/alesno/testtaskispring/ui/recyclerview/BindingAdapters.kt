package com.alesno.testtaskispring.ui.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.model.response.Video

@BindingAdapter("data")
fun setItems(recyclerView: RecyclerView, videos: List<Video>){
    (recyclerView.adapter as VideoAdapter).replaceVideo(videos)
}
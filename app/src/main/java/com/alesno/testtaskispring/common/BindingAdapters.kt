package com.alesno.testtaskispring.common

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.model.objectbox.entities.ExpertObject
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview.VideoListAdapter
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.ExpertsAdapter
import com.alesno.testtaskispring.ui.videoactivity.recyclerview.TopicsAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("setItems")
fun setItem(recyclerView: RecyclerView, videos: List<VideoObject>) {
    (recyclerView.adapter as VideoListAdapter).replaceData(videos)
}

@BindingAdapter("setTopics")
fun setTopics(recyclerView: RecyclerView, topics: List<String>) {
    (recyclerView.adapter as TopicsAdapter).replaceData(topics)
}

@BindingAdapter("setExperts")
fun setExperts(recyclerView: RecyclerView, experts: List<ExpertObject>) {
    (recyclerView.adapter as ExpertsAdapter).replaceData(experts)
}

@BindingAdapter("url", "errorImage")
fun setPicture(imageView: ImageView, url: String, errorImage: Drawable) {
    Picasso.get().load(url).placeholder(errorImage).error(errorImage).fit().into(imageView)
}

@BindingAdapter("setVideoUrl")
fun setVideoUrl(videoView: VideoView, url: String) {
    Log.d("log", url)
    videoView.setVideoPath(url)
}


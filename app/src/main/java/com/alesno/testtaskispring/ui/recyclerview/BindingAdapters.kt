package com.alesno.testtaskispring.ui.recyclerview

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.squareup.picasso.Picasso


@BindingAdapter("setItems")
fun setItem(recyclerView: RecyclerView, videos: List<VideoObject>){
    (recyclerView.adapter as VideoAdapter).replaceVideo(videos)
}

@BindingAdapter("url", "errorImage")
fun setPicture(imageView: ImageView, url: String, errorImage: Drawable){
    Picasso.get().load(url).error(errorImage).into(imageView)
}

package com.alesno.testtaskispring.common

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
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
    videoView.setVideoPath(url)
}

@BindingAdapter("setTextColorByProgress")
fun setTextColorByProgress(textView: TextView, progress: Int) {
    when (progress) {
        0 -> textView.setTextColor(Color.RED)
        in 1..97 -> textView.setTextColor(Color.GRAY)
        in 98..100 -> textView.setTextColor(Color.GREEN)
    }
}

@BindingAdapter("setTextProgressByProgress")
fun setTextProgressByProgress(textView: TextView, progress: Int) {
    val testProgress = "В процессе ($progress%)"
    when (progress) {
        0 -> textView.text = "Не начато"
        in 1..97 -> textView.text = testProgress
        in 98..100 -> textView.text = "Просмотренно"
    }
}

@BindingAdapter("isFavorite", "isDataBeOut", "isFavoriteBeOut")
fun setTextWhenDataIsOut(
    textView: TextView,
    isFavoriteList: Boolean,
    isDataBeOut: Boolean,
    isFavoriteBeOut: Boolean
) {
    val videoIsOut = textView.context.getString(R.string.video_is_out)
    val favoriteVideoIsOut = textView.context.getString(R.string.you_do_not_have_favorite_videos)
    when {
        !isFavoriteList && isDataBeOut -> textView.text = videoIsOut
        !isFavoriteList && !isDataBeOut -> textView.text = ""
        isFavoriteList && isFavoriteBeOut -> textView.text = favoriteVideoIsOut
        isFavoriteList && !isFavoriteBeOut -> textView.text = ""
    }
}





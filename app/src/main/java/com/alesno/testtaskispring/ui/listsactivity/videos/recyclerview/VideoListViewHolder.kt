package com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.ui.videoactivity.VideoActivity

class VideoListViewHolder(
    val binding: ItemListMovieBinding,
    private val mCheckedCallBack: (idVideo: Long, isFavorite: Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { item ->
            VideoActivity.startActivity(
                item.context,
                videoObject.id,
                videoObject.url.toString()
            )
        }
    }

    lateinit var videoObject: VideoCommonDomain

    fun bind(video: VideoCommonDomain) {
        videoObject = video
        binding.video = video
        binding.checkboxFavorite.isChecked = video.isFavorite
        binding.executePendingBindings()
        val checkBox = binding.checkboxFavorite
        checkBox.setOnClickListener {
            mCheckedCallBack(video.id, checkBox.isChecked)
        }
    }

}
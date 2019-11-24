package com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel
import com.alesno.testtaskispring.ui.videoactivity.VideoActivity

class VideoListViewHolder(val binding: ItemListMovieBinding, val viewModel: CommonViewModel) :
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

    lateinit var videoObject: VideoObject

    fun bind(video: VideoObject) {
        videoObject = video
        binding.video = video
        binding.checkboxFavorite.isChecked = video.isFavorite
        binding.executePendingBindings()
        val checkBox = binding.checkboxFavorite
        checkBox.setOnClickListener {
            viewModel.onCheckboxClicked(
                video.id,
                checkBox.isChecked
            )
        }
    }

}
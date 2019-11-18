package com.alesno.testtaskispring.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.objectbox.entity.VideoObj
import com.alesno.testtaskispring.model.response.Video

class VideoViewHolder(val binding: ItemListMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(video: VideoObj){
        binding.video = video
        binding.executePendingBindings()
    }
}
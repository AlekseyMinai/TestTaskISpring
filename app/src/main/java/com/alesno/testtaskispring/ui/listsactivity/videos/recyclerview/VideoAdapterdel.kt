package com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject

class VideoAdapterdel: RecyclerView.Adapter<VideoViewHolderdel>() {


    var videos: MutableList<VideoObject> = mutableListOf()

    fun replaceVideo(videos: List<VideoObject>){
        this.videos.clear()
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolderdel {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListMovieBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_movie, parent, false)
        return VideoViewHolderdel(binding)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoViewHolderdel, position: Int) {
        holder.bind(videos[position])
    }

}
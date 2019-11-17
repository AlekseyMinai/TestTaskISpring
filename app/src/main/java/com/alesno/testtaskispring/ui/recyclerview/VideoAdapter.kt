package com.alesno.testtaskispring.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.response.Video

class VideoAdapter: RecyclerView.Adapter<VideoViewHolder>() {


    private val videos: MutableList<Video> = mutableListOf()

    fun replaceVideo(videos: List<Video>){
        this.videos.clear()
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListMovieBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_movie, parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        //holder.bind(videos[position])
    }

}
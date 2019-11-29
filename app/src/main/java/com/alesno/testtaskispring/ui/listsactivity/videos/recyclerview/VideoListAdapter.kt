package com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.ItemListMovieBinding
import com.alesno.testtaskispring.model.domain.VideoCommonDomain
import com.alesno.testtaskispring.ui.base.BaseRWAdapter

class VideoListAdapter(private val mCheckedCallBack: (idVideo: Long, isFavorite: Boolean) -> Unit) :
    BaseRWAdapter<VideoListViewHolder, VideoCommonDomain>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_list_movie, parent, false)
        return VideoListViewHolder(binding, mCheckedCallBack)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(list[position])
    }

}
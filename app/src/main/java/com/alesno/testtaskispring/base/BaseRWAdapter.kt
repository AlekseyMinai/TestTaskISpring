package com.alesno.testtaskispring.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRWAdapter<D: ViewDataBinding, T: BaseViewHolder<D>, G>
    (private val resource: Int): RecyclerView.Adapter<T>() {

    var list: MutableList<G> = mutableListOf()

    fun replaceVideo(videos: List<G>){
        this.list.clear()
        this.list.addAll(videos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: D = DataBindingUtil.inflate(inflater, resource, parent, false)
        return BaseViewHolder(binding) as T
    }

}
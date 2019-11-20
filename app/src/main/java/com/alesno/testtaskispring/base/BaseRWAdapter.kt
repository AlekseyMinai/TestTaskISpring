package com.alesno.testtaskispring.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRWAdapter<H: RecyclerView.ViewHolder, D>: RecyclerView.Adapter<H>() {

    var list: MutableList<D> = mutableListOf()

    fun replaceData(data: List<D>){
        this.list.clear()
        this.list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
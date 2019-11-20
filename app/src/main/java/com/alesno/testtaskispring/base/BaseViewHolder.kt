package com.alesno.testtaskispring.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<D : ViewDataBinding>(val binding: D): RecyclerView.ViewHolder(binding.root)
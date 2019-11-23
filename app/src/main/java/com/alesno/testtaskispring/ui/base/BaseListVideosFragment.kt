package com.alesno.testtaskispring.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.FragmentListMoviesBinding
import com.alesno.testtaskispring.ui.listsactivity.videos.FragmentListActivity
import com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview.VideoListAdapter
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel
import kotlinx.android.synthetic.main.fragment_list_movies.*

abstract class BaseListVideosFragment : Fragment() {

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)
        setupRecyclerView()
        return binding.root
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?){
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        viewModel = FragmentListActivity.getCommonViewModel(activity!!)
        binding.viewModel = viewModel
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = VideoListAdapter(viewModel)
    }

}
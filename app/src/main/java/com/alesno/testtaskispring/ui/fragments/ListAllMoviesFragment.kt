package com.alesno.testtaskispring.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.FragmentListMoviesBinding
import com.alesno.testtaskispring.ui.FragmentActivity
import com.alesno.testtaskispring.ui.recyclerview.VideoAdapter
import com.alesno.testtaskispring.ui.viewmodel.CommonViewModel
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListAllMoviesFragment: Fragment() {

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: CommonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)

        viewModel = FragmentActivity.getCommonViewModel(activity!!)

        binding.viewModel = viewModel

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeToRefreshCallBack()
        viewModel.onViewCreated()
    }

    private fun setupRecyclerView(){
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = VideoAdapter()
    }


    private fun setupSwipeToRefreshCallBack() {
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_to_refresh.setOnRefreshListener { swipe_to_refresh.isRefreshing = false }
    }
}
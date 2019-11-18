package com.alesno.testtaskispring.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.FragmentListMoviesBinding
import com.alesno.testtaskispring.model.repository.ApiRepository
import com.alesno.testtaskispring.model.service.ApiService
import com.alesno.testtaskispring.ui.recyclerview.VideoAdapter
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListAllMoviesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentListMoviesBinding
                = FragmentListMoviesBinding
            .inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = VideoAdapter()

        val viewModel: CommonViewModel = FragmentActivity.getCommonViewModel(activity!!)

        binding.viewModel = viewModel

        viewModel.getResponseAsync()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeToRefreshCallBack()
    }


    private fun setupSwipeToRefreshCallBack() {
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_to_refresh.setOnRefreshListener { swipe_to_refresh.isRefreshing = false }
    }
}
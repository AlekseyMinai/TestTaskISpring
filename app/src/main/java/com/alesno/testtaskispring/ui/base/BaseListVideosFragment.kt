package com.alesno.testtaskispring.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.databinding.FragmentListMoviesBinding
import com.alesno.testtaskispring.ui.listsactivity.videos.FragmentListActivity
import com.alesno.testtaskispring.ui.listsactivity.videos.recyclerview.VideoListAdapter
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel
import com.google.android.material.snackbar.Snackbar
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
        setupSwipeToRefreshCallBack()
        return binding.root
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        viewModel = FragmentListActivity.getCommonViewModel(activity!!)
        binding.viewModel = viewModel
        binding.isFavorite = isListFavorite()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = VideoListAdapter { idVideo, isChecked ->
            viewModel.onCheckboxClicked(
                idVideo,
                isChecked
            )
        }
    }

    private fun setupSwipeToRefreshCallBack() {
        binding.swipeToRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeToRefresh.setOnRefreshListener {
            refreshData()
        }
        viewModel.isRefreshedLiveData.observe(this, Observer { swipe_to_refresh.isRefreshing = it })
    }

    protected abstract fun isListFavorite(): Boolean

    protected abstract fun refreshData()

}


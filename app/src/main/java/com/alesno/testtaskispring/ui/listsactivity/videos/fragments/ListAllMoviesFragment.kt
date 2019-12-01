package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import androidx.lifecycle.Observer
import com.alesno.testtaskispring.ui.base.BaseListVideosFragment
import com.google.android.material.snackbar.Snackbar

class ListAllMoviesFragment : BaseListVideosFragment() {

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
        setupSnackBarEventListener()
    }

    override fun refreshData() {
        viewModel.onRefreshedListAllVideos()
    }

    override fun isListFavorite(): Boolean {
        return false
    }

    private fun setupSnackBarEventListener() {
        viewModel.snackBarEventListener.observe(this, Observer { showSnackBar(it) })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_LONG).show()
    }

}
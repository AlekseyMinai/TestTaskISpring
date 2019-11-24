package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import com.alesno.testtaskispring.ui.base.BaseListVideosFragment
import kotlinx.android.synthetic.main.fragment_list_movies.*

class ListFavoriteMoviesFragment : BaseListVideosFragment() {

    override fun refreshData() {
        swipe_to_refresh.isRefreshing = false
    }


    override fun isListFavorite(): Boolean {
        return true
    }
}
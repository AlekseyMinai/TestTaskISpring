package com.alesno.testtaskispring.ui.listsactivity.videos.fragments

import android.os.Bundle
import android.view.View
import com.alesno.testtaskispring.ui.base.BaseListVideosFragment

class ListFavoriteMoviesFragment : BaseListVideosFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onListFavoriteVideosCreated()
    }

    override fun isListFavorite(): Boolean {
        return true
    }
}